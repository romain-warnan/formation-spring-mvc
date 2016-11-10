package fr.insee.bar.service;

import org.springframework.stereotype.Service;

import com.google.common.base.Objects;

import fr.insee.bar.model.Employe;
import fr.insee.bar.model.Role;

@Service
public class EmployeService {

	private static final Short RESPONSABLE = Short.valueOf("2");

	public boolean estResponsable(Employe employe) {
		Role role = employe.getRole();
		if (role == null) {
			return false;
		}
		return Objects.equal(role.getId(), RESPONSABLE);
	}

	// TODO public void verifierResponsable(Employe employe) throws BarDroitException;
}
