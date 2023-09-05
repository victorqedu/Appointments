package com.caido.appointments.services;

import com.caido.appointments.Util.JWT;
import com.caido.appointments.entity.DTO.ConsultatiiDTO;
import com.caido.appointments.entity.Person;
import com.caido.appointments.repositories.ConsultatiiRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ConsultatiiService {
    ConsultatiiRepository consultatiiRepository;

    public ConsultatiiService(ConsultatiiRepository cr) {
        this.consultatiiRepository = cr;
    }

    public ConsultatiiRepository getPersonRepository() {
        return consultatiiRepository;
    }
    
    public List<ConsultatiiDTO> getPatientConsultations(Person idPerson, Integer recordsPerPage, Integer pageNumber, String jwtToken) {
        String idUserConectat = JWT.getClaimByNameFromToken(jwtToken, "id");
        if(!idUserConectat.equals(idPerson.getId().toString())) {
            throw new RuntimeException("Tentativa de extragere date a fost identificata.");
        } else {
            List<ConsultatiiDTO> c = consultatiiRepository.getPatientConsultations(idPerson.getId(), recordsPerPage, pageNumber);
            System.out.println("c "+c);
            return c;
        }
    }
    
    public Integer countPatientConsultations(Person idPerson, String jwtToken) {
        String idUserConectat = JWT.getClaimByNameFromToken(jwtToken, "id");
        if(!idUserConectat.equals(idPerson.getId().toString())) {
            throw new RuntimeException("Tentativa de extragere date a fost identificata.");
        } else {
            return consultatiiRepository.countPatientConsultations(idPerson.getId());
        }
        
    }
}
