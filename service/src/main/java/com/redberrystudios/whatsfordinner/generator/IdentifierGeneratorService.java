package com.redberrystudios.whatsfordinner.generator;

import java.util.function.Function;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

@Service
public class IdentifierGeneratorService {

  private static final Long MIN_BOUNDARY = 0L;

  private static final Long MAX_BOUNDARY = 2L << 52;

  private static final int STRING_LENGTH = 12;

  public Long generateLongIdentifier(Function<Long, Boolean> isReservedFunction) {
    Long id;
    do {
      id = RandomUtils.nextLong(MIN_BOUNDARY, MAX_BOUNDARY);
    } while (isReservedFunction.apply(id));

    return id;
  }

  public String generateStringIdentifier(Function<String, Boolean> isReservedFunction) {
    String token;
    do {
      token = RandomStringUtils.randomAlphanumeric(STRING_LENGTH);
    } while (isReservedFunction.apply(token));

    return token;
  }
}
