package com.es3.user.seller.domain;

import com.es3.user.common.entity.BaseEntity;
import com.es3.user.config.exception.AuthException;
import com.es3.user.config.exception.ErrorCode;
import com.es3.user.constants.Role;
import com.es3.user.security.EncryptionUtil;
import com.es3.user.seller.dto.request.SellerSignUpForm;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_sellers")
public class Seller extends BaseEntity implements UserDetails {

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

	@Builder
	public Seller(String email, String password, String name, String mobile, boolean sellerStatus, Role role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.mobile = mobile;
		this.sellerStatus = sellerStatus;
		this.role = role;
	}

	public static Seller createSeller(SellerSignUpForm.BasicInfo basicInfo) throws Exception {
		return Seller.builder()
				.email(basicInfo.email())
				.password(EncryptionUtil.encrypt(basicInfo.password()))
				.name(basicInfo.name())
				.mobile(basicInfo.mobile())
				.sellerStatus(false)
				.role(Role.SELLER)
				.build();
	}

	public void verifyPassword(String password) throws Exception {
		if (!password.equals(EncryptionUtil.decrypt(this.password))) {
			throw new AuthException(ErrorCode.INVALID_CREDENTIAL);
		}
	}

	public Seller updateEmail(String email){
		this.email = email;
		return this;
	}

	public Seller updateMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	public Seller updateName(String name) {
		this.name = name;
		return this;
	}
}


