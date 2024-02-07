package com.myEdu.ws.service;

import com.myEdu.ws.model.ProgramOutcome;
import com.myEdu.ws.repository.ProgramOutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramOutcomeService {

    private final ProgramOutcomeRepository programOutcomeRepository;

    @Autowired
    public ProgramOutcomeService(ProgramOutcomeRepository programOutcomeRepository) {
        this.programOutcomeRepository = programOutcomeRepository;
    }

    // Tüm program çıktılarını getir
    public List<ProgramOutcome> getAllProgramOutcomes() {
        return programOutcomeRepository.findAll();
    }

    // Belirli bir program çıktısını ID'ye göre getir
    public Optional<ProgramOutcome> getProgramOutcomeById(Long id) {
        return programOutcomeRepository.findById(id);
    }

    // Yeni bir program çıktısı oluştur
    public ProgramOutcome createProgramOutcome(ProgramOutcome programOutcome) {
        return programOutcomeRepository.save(programOutcome);
    }

    // Program çıktısını güncelle
    public ProgramOutcome updateProgramOutcome(Long id, ProgramOutcome newProgramOutcome) {
        if (programOutcomeRepository.existsById(id)) {
            newProgramOutcome.setId(id);
            return programOutcomeRepository.save(newProgramOutcome);
        } else {
            // Belirli bir ID ile program çıktısı bulunamazsa null dönebilir veya isteğe göre bir hata işleme stratejisi uygulanabilir
            return null;
        }
    }

    // Program çıktısını sil
    public void deleteProgramOutcome(Long id) {
        programOutcomeRepository.deleteById(id);
    }
}
