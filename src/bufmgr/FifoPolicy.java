
package bufmgr;

import diskmgr.*;
import global.*;

import java.util.ArrayList;

/**
   * class Policy is a subclass of class Replacer use the given replacement
   * policy algorithm for page replacement
   */
class FifoPolicy extends  Replacer implements Policy {
//replace Policy above with impemented policy name (e.g., Lru, Clock)

  //
  // Frame State Constants
  //
  protected static final int AVAILABLE = 10;
  protected static final int REFERENCED = 11;
  protected static final int PINNED = 12;

  private ArrayList<Integer> frameIndexList = new ArrayList<>();

  //Following are the fields required for LRU and MRU policies:
  /**
   * private field
   * An array to hold number of frames in the buffer pool
   */

//    private int  frames[];
 
  /**
   * private field
   * number of frames used
   */   
//  private int  nframes;

  /** Clock head; required for the default clock algorithm. */
//  protected int head;

  /**
   * This pushes the given frame to the end of the list.
   * @param frameNo	the frame number
   */
//  private void update(int frameNo)
//  {
//     //This function is to be used for LRU and MRU
//  }

  /**
   * Class constructor
   * Initializing frames[] pinter = null.
   */
    public FifoPolicy(BufMgr mgrArg)
    {
      super(mgrArg);
      // initialize the frame states
    for (int i = 0; i < frametab.length; i++) {
      frametab[i].state = AVAILABLE;
    }
      // initialize parameters for LRU and MRU
//      nframes = 0;
//      frames = new int[frametab.length];

    // initialize the clock head for Clock policy
//    head = -1;
    }
  /**
   * Notifies the replacer of a new page.
   */
  public void newPage(FrameDesc fdesc) {
    // no need to update frame state
  }

  /**
   * Notifies the replacer of a free page.
   */
  public void freePage(FrameDesc fdesc) {
    fdesc.state = AVAILABLE;
  }

  /**
   * Notifies the replacer of a pined page.
   */
  public void pinPage(FrameDesc fdesc) {
    // make the state pinned, if it already is it will remain pinned
    frametab[fdesc.index].state = PINNED;

    // add to index of frame to stack if its not in the stack
    if(!frameIndexList.contains(fdesc.index)) {
      // add index to end of frameIndexList
      frameIndexList.add(fdesc.index);
    }

  }

  /**
   * Notifies the replacer of an unpinned page.
   */
  public void unpinPage(FrameDesc fdesc) {
    if (fdesc.pincnt == 0) {
      frametab[fdesc.index].state = REFERENCED;
    } else {
      frametab[fdesc.index].state = PINNED;
    }
  }
  
  /**
   * Finding a free frame in the buffer pool
   * or choosing a page to replace using your policy
   *
   * @return 	return the frame number
   *		return -1 if failed
   */

 public int pickVictim()
 {
   // see if we can return one from the frame index list
   for (int i = 0; i < frametab.length; i++) {
     if (frametab[i].state == AVAILABLE) {
       return i;
     }
   }

   // pop the first index that can be replaced and return it
   for (int i =0; i < frameIndexList.size(); i++) {
     int frametabIndex = frameIndexList.get(i);
     if (frametab[frametabIndex].state == REFERENCED) {
       frameIndexList.remove(i);
       return frametabIndex;
     }
   }

   // if nothing is found return -1
   return -1;
  }
 }

