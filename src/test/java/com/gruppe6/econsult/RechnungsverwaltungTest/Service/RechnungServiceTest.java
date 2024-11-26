import com.gruppe6.econsult.Rechnungsverwaltung.Entity.Rechnung;
import com.gruppe6.econsult.Rechnungsverwaltung.Repository.RechnungRepository;
import com.gruppe6.econsult.Rechnungsverwaltung.Service.RechnungService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RechnungServiceTest {

    @Mock
    private RechnungRepository rechnungRepository;

    @InjectMocks
    private RechnungService rechnungService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test für createRechnung
    @Test
    void createRechnung_shouldSaveAndReturnRechnung() {
        // Mock-Daten vorbereiten
        Long patientId = 1L;
        String rechnungNummer = UUID.randomUUID().toString();
        Rechnung mockRechnung = new Rechnung(rechnungNummer, patientId);
        mockRechnung.setDatum(new Date());
        mockRechnung.setBetrag(20);

        // Mock-Verhalten
        when(rechnungRepository.save(any(Rechnung.class))).thenReturn(mockRechnung);

        // Methode aufrufen
        Rechnung result = rechnungService.createRechnung(patientId);

        // Überprüfen
        assertNotNull(result);
        assertEquals(patientId, result.getIdPatient());
        assertEquals(20, result.getBetrag());
        verify(rechnungRepository, times(1)).save(any(Rechnung.class));
    }

    // Test für getRechnungenForPatient
    @Test
    void getRechnungenForPatient_shouldReturnListOfRechnungen() {
        // Mock-Daten vorbereiten
        Long patientId = 1L;
        Rechnung rechnung1 = new Rechnung("123-abc", patientId);
        rechnung1.setDatum(new Date());
        rechnung1.setBetrag(20);

        Rechnung rechnung2 = new Rechnung("456-def", patientId);
        rechnung2.setDatum(new Date());
        rechnung2.setBetrag(20);

        List<Rechnung> mockRechnungen = Arrays.asList(rechnung1, rechnung2);

        // Mock-Verhalten
        when(rechnungRepository.findByIdPatient(patientId)).thenReturn(mockRechnungen);

        // Methode aufrufen
        List<Rechnung> result = rechnungService.getRechnungenForPatient(patientId);

        // Überprüfen
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(rechnungRepository, times(1)).findByIdPatient(patientId);
    }

    // Test für getRechnungById
    @Test
    void getRechnungById_shouldReturnRechnungWhenFound() {
        // Mock-Daten vorbereiten
        Long rechnungId = 1L;
        Rechnung mockRechnung = new Rechnung("123-abc", 1L);
        mockRechnung.setId(rechnungId);
        mockRechnung.setDatum(new Date());
        mockRechnung.setBetrag(20);

        // Mock-Verhalten
        when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.of(mockRechnung));

        // Methode aufrufen
        Optional<Rechnung> result = rechnungService.getRechnungById(rechnungId);

        // Überprüfen
        assertTrue(result.isPresent());
        assertEquals(rechnungId, result.get().getId());
        verify(rechnungRepository, times(1)).findById(rechnungId);
    }

    @Test
    void getRechnungById_shouldReturnEmptyWhenNotFound() {
        // Mock-Daten vorbereiten
        Long rechnungId = 1L;

        // Mock-Verhalten
        when(rechnungRepository.findById(rechnungId)).thenReturn(Optional.empty());

        // Methode aufrufen
        Optional<Rechnung> result = rechnungService.getRechnungById(rechnungId);

        // Überprüfen
        assertFalse(result.isPresent());
        verify(rechnungRepository, times(1)).findById(rechnungId);
    }

    // Test für deleteRechnung
    @Test
    void deleteRechnung_shouldDeleteRechnung() {
        // Mock-Daten vorbereiten
        Long rechnungId = 1L;

        // Methode aufrufen
        rechnungService.deleteRechnung(rechnungId);

        // Überprüfen
        verify(rechnungRepository, times(1)).deleteById(rechnungId);
    }
}
