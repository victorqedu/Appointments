package com.caido.appointments.services;

import com.caido.appointments.entity.DTO.ScrisoareMedicalaDTO;
import com.caido.appointments.repositories.ScrisoareMedicalaRepository;
import org.springframework.stereotype.Service;

@Service
public class ScrisoareMedicalaService {
    ScrisoareMedicalaRepository scrisoareMedicalaRepository;

    public ScrisoareMedicalaService(ScrisoareMedicalaRepository cr) {
        this.scrisoareMedicalaRepository = cr;
    }

    public ScrisoareMedicalaDTO get(Integer idScrisoareMedicala, String jwtToken) {
        String idUserConectat = com.caido.appointments.Util.JWT.getClaimByNameFromToken(jwtToken, "id");
        Integer smPersonID = scrisoareMedicalaRepository.getPersonId(idScrisoareMedicala);
        if(!idUserConectat.equals(smPersonID+"")) {
            throw new RuntimeException("Tentativa de extragere date a fost identificata(persoana de pe scrisoarea medicala nu corespunde contului conectat idUserConectat="+idUserConectat+" smPersonID="+smPersonID+").");
        } else {
            return scrisoareMedicalaRepository.get(idScrisoareMedicala);
        }
    }
    
}
