package courses.innotech;

import courses.innotech.accounts.Account;
import courses.innotech.currencies.ECurrency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestsAccountsPart1Create {
  @Test
  public void clientNameNull(){
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> new Account(null),
        "Имя не может быть null или пустым");
  }

  @Test
  public void currencyCountNegative(){
    Account account = new Account("Владимир");
    Assertions.assertThrows(IllegalArgumentException.class,
        () -> account.changeCurrencyCount(ECurrency.RUB, -1),
        "Количество валюты не может быть отрицательным");
  }
}
