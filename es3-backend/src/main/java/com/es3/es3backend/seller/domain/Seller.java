package com.es3.es3backend.seller.domain;

import com.es3.es3backend.auth.security.EncryptionUtil;
import com.es3.es3backend.common.entity.BaseEntity;
import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.constants.Role;
import com.es3.es3backend.store.domain.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
@Table(name = "tb_sellers")
public class Seller extends BaseEntity implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

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

	//business registration number
	@Column(name = "brn", nullable = false)
	private String brn;

	@Column(name = "bank") // nullable = false
	private String bank;
	@Column(name = "account_number") // nullable = false
	private String accountNumber;
	@Column(name = "account_holder") // nullable = false
	private String accountHolder;
	@Column(name = "id_number")
	private String idNumber;
	@Column(name = "seller_status")
	private boolean sellerStatus = false;

	@Column(name = "role")
	@Enumerated(value = EnumType.STRING)
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities().stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
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

	public void connectStore(Store store) {
		this.store = store;
	}
}


