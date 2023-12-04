package courses.innotech.accounts;

public class KeyValue {
  final String key;
  final Object value;

  public KeyValue(String key, Object value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String toString() {
    return "KeyValue{" +
        "key='" + key + '\'' +
        ", value=" + value +
        '}';
  }
}
