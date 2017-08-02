package redis.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component(value = "accountService")
public class FakeAccountServiceImpl implements FakeAccountService {

	@Override
	//@Cacheable(value = "accountCache", key = "#identity")
	public FakeAccount findAccountIdByIdentity(String identity) {
		System.out.println("call from db");
		FakeAccount account = new FakeAccount();
		account.setAccountId(2L);
		account.setName("mike");
		return account;
	}

}
