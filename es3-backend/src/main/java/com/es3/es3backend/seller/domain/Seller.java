package com.es3.es3backend.seller.domain;

import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.constants.Role;
import com.es3.es3backend.security.EncryptionUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Seller implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "mobile", nullable = false)
	private String mobile;
	@Column(name = "post_code", nullable = false)
	private String postCode;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "brn", nullable = false)
	private String brn;

	@Column(name = "bank") // nullable = false
	private String bank;
	@Column(name = "account_number") // nullable = false
	private String accountNumber;
	@Column(name = "account_holder") // nullable = false
	private String accountHolder;

	@Column(name = "seller_status")
	private boolean sellerStatus = false;

	@Column(name = "role")
	@Enumerated(value = EnumType.STRING)
	private Role role = Role.SELLER;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.getRoles()));
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	public void verifyPassword(String password) throws Exception {
		if (!password.equals(EncryptionUtil.decrypt(this.password))) {
			throw new AuthException(ErrorCode.INVALID_CREDENTIAL);
		}
	}
}


