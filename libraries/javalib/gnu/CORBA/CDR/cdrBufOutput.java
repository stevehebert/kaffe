/* cdrBufOutput.java --
   Copyright (C) 2005 Free Software Foundation, Inc.

This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
02111-1307 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */


package gnu.CORBA.CDR;

import java.io.ByteArrayOutputStream;

/**
 * A CORBA output stream, writing data into the internal
 * buffer ({@link ByteArrayOutputStream}).
 *
 * @author Audrius Meskauskas (AudriusA@Bioinformatics.org)
 */
public class cdrBufOutput
  extends cdrOutput
{
  /**
   * The byte buffer.
   */
  public final aligningOutputStream buffer;

  /**
   * Creates the instance with the given initial buffer size.
   * @param bufSize the buffer size.
   */
  public cdrBufOutput(int bufSize)
  {
    buffer = new aligningOutputStream(bufSize);
    setOutputStream(buffer);
  }

  /**
   * Creates the instance with the default buffer size.
   */
  public cdrBufOutput()
  {
    buffer = new aligningOutputStream();
    setOutputStream(buffer);
  }

  /**
   * Set the alignment offset, if the index of the first byte in the
   * stream is different from 0.
   */
  public void setOffset(int an_offset)
  {
    buffer.setOffset(an_offset);
  }

  /**
   * Align the curretn position at the given natural boundary.
   */
  public void align(int boundary)
  {
    buffer.align(boundary);
  }

  /**
   * Return the input stream that reads the previously written values.
   */
  public org.omg.CORBA.portable.InputStream create_input_stream()
  {
    cdrBufInput in = new cdrBufInput(buffer.toByteArray());
    in.setOrb(orb);

    in.setVersion(giop);
    in.setCodeSet(getCodeSet());

    return in;
  }

  /**
   * Resets (clears) the buffer.
   */
  public void reset()
  {
    buffer.reset();
    setOutputStream(buffer);
  }
}
