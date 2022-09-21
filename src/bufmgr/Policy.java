package bufmgr;

public interface Policy {
  void newPage(FrameDesc fdesc);
  public void freePage(FrameDesc fdesc);
  public void pinPage(FrameDesc fdesc);
  public void unpinPage(FrameDesc fdesc);
  public int pickVictim();
}
