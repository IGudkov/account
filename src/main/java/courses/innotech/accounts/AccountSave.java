package courses.innotech.accounts;

import courses.innotech.currencies.ECurrency;
import lombok.Getter;
import lombok.ToString;

import java.util.EnumMap;
import java.util.Map;
import java.util.Stack;

@ToString
@Getter
public final class AccountSave {

  private final String clientName;
  private final Map<ECurrency,Integer> currencyCount;
  private final Stack<KeyValue> stackUndo;

  public AccountSave(String clientName, Map<ECurrency, Integer> currencyCount, Stack<KeyValue> stackUndo) {
    this.clientName = clientName;
    if (!currencyCount.isEmpty())
      this.currencyCount = Map.copyOf(currencyCount);
    else
      this.currencyCount = new EnumMap<>(ECurrency.class);
    if (!stackUndo.isEmpty())
      this.stackUndo = (Stack<KeyValue>)stackUndo.clone();
    else
      this.stackUndo = new Stack<>();
  }
}
