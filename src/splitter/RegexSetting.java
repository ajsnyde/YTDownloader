package splitter;

public class RegexSetting {
  public String pre;
  public String regex;
  public String exampleInput = "no example";
  public int titleGroup = -1;
  public int timestampGroup = -1;

  RegexSetting(String pre, int titleGroup, int timestampGroup) {
    this.pre = pre;
    this.titleGroup = titleGroup;
    this.timestampGroup = timestampGroup;
    this.regex = RegexHelper.regexProcess(pre);
  }

  RegexSetting(String pre, int titleGroup, int timestampGroup, String exampleInput) {
    this.pre = pre;
    this.titleGroup = titleGroup;
    this.timestampGroup = timestampGroup;
    this.regex = RegexHelper.regexProcess(pre);
    this.exampleInput = exampleInput;
  }

  public String toString() {
    return pre;
  }
}
