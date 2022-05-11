package edge.project3.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edge.project3.module.Otp;

public interface OtpRepository extends JpaRepository<Otp, String> {
	Optional<Otp> findOtpByUsername(String username);
}
