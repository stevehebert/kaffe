/* aligningInputStream.java --
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

import java.io.ByteArrayInputStream;

import org.omg.CORBA.BAD_PARAM;

/**
 * The input stream with the possibility to align on the
 * word (arbitrary size) boundary.
 *
 * @author Audrius Meskauskas (AudriusA@Bioinformatics.org)
 */
public class aligningInputStream
  extends ByteArrayInputStream
{
  /**
   * The alignment offset.
   */
  private int offset = 0;

  /**
   * Create a stream, reading form the given buffer.
   *
   * @param a_buffer a buffer to read from.
   */
  public aligningInputStream(byte[] a_buffer)
  {
    super(a_buffer);
  }

  /**
   * Create a stream, reading from the given buffer region.
   *
   * @param a_buffer a buffer to read from.
   * @param offset the offset of the region.
   * @param length thr length of the region.
   */
  public aligningInputStream(byte[] a_buffer, int offset, int length)
  {
    super(a_buffer, offset, length);
  }

  /**
   * Set the alignment offset, if the index of the first byte in the
   * stream is different from 0.
   */
  public void setOffset(int an_offset)
  {
    offset = an_offset;
  }

  /**
   * Skip several bytes, aligning the internal pointer on the
   * selected boundary.
   *
   * @throws BAD_PARAM, minor code 0, the alignment is not possible,
   * usually due the wrong parameter value.
   */
  public void align(int alignment)
  {
    try
      {
        int d = (pos + offset) % alignment;
        if (d > 0)
          {
            skip(alignment - d);
          }
      }
    catch (Exception ex)
      {
        throw new BAD_PARAM("Unable to align at " + alignment);
      }
  }
}
