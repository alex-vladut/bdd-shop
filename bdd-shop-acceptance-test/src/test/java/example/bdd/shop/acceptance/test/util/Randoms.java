package example.bdd.shop.acceptance.test.util;

import java.util.Random;
import java.util.UUID;

public final class Randoms {
	private static final String CHARSET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
	public static String randomString(final int length) {
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(randomChar());
		}
		return sb.toString();
	}

	public static char randomChar() {
		final int pos = newRandom().nextInt(CHARSET.length());
		return CHARSET.charAt(pos);
	}
	
    private static Random newRandom() {
        final UUID uuid = UUID.randomUUID();
        return new Random(uuid.getMostSignificantBits() * uuid.getLeastSignificantBits());
    }
}
