package com.volmy.service;

import com.volmy.model.Donor;
import com.volmy.model.Fundraising;
import com.volmy.repository.FundraisingRepository;
import com.volmy.service.fileparser.FundraisingXLSParser;
import com.volmy.service.importcontext.ImportContext;
import io.micronaut.http.multipart.CompletedFileUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FundraisingServiceTest {

    @Mock
    private FundraisingRepository repository;

    @Mock
    private FundraisingXLSParser parser;

    @Mock
    private ImportContext importContext;

    @Mock
    private CompletedFileUpload file;

    @InjectMocks
    private FundraisingService service;

    @BeforeEach
    void startImport() {
        when(importContext.startNew()).thenReturn("import-id");
    }

    @Test
    void savesNewFundraisingAndDonorsInOneBatch() {
        Fundraising candidate = fundraising("New");
        Donor donor = new Donor();
        donor.setName("Donor");
        candidate.addDonor(donor);
        when(parser.parse(file, "import-id")).thenReturn(Collections.singletonList(candidate));

        assertEquals("import-id", service.importFundraisings(file));
        verify(importContext, timeout(5000)).complete("import-id");
        verify(repository).saveAll(Collections.singletonList(candidate));
        assertEquals(candidate, donor.getFundraising());
    }

    @Test
    void filtersExistingAndLaterRepeatedNamesBeforeSaving() {
        Fundraising existing = fundraising("Existing");
        Fundraising first = fundraising("New");
        Fundraising repeated = fundraising("New");
        Fundraising other = fundraising("Other");
        when(repository.existsByName("Existing")).thenReturn(true);
        when(parser.parse(file, "import-id")).thenReturn(Arrays.asList(existing, first, repeated, other));

        service.importFundraisings(file);
        verify(importContext, timeout(5000)).complete("import-id");
        verify(repository).saveAll(Arrays.asList(first, other));
        verify(repository, never()).update(any());
    }

    @Test
    void preservesExactCaseWhenFilteringNames() {
        Fundraising first = fundraising("Case");
        Fundraising second = fundraising("case");
        when(parser.parse(file, "import-id")).thenReturn(Arrays.asList(first, second));

        service.importFundraisings(file);
        verify(importContext, timeout(5000)).complete("import-id");
        verify(repository).saveAll(Arrays.asList(first, second));
    }

    @Test
    void completesWhenAllFundraisingsAlreadyExist() {
        when(repository.existsByName("Existing")).thenReturn(true);
        when(parser.parse(file, "import-id")).thenReturn(Collections.singletonList(fundraising("Existing")));

        service.importFundraisings(file);
        verify(importContext, timeout(5000)).complete("import-id");
        verify(repository).saveAll(Collections.emptyList());
    }

    @Test
    void doesNotReportSuccessAfterBatchPersistenceFailure() {
        Fundraising candidate = fundraising("New");
        when(parser.parse(file, "import-id")).thenReturn(Collections.singletonList(candidate));
        doThrow(new IllegalStateException("Persistence failure")).when(repository).saveAll(any());

        service.importFundraisings(file);
        verify(repository, timeout(5000)).saveAll(Collections.singletonList(candidate));
        verify(importContext, after(100).never()).complete("import-id");
    }

    private static Fundraising fundraising(String name) {
        Fundraising result = new Fundraising();
        result.setName(name);
        return result;
    }
}
