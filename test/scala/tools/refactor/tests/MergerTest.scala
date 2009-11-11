package scala.tools.refactor.tests

import utils.TestHelper
import org.junit.Test
import junit.framework.TestCase
import org.junit.Assert._
import scala.tools.refactor.printer._

@Test
class MergerTest extends TestCase with TestHelper {

  def testSortClassParameters() = {
    "class A(i: Int, s: String)" transformsTo ("class A(s: String, i: Int)", reverseClassParameters.transform(_))
    "class A(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int)" transformsTo ("class A(i5: Int, i4: Int, i3: Int, i2: Int, i1: Int)", reverseClassParameters.transform(_))
    "class A(/*1a*/i:/*1b*/Int/*1c*/, /*2a*/s: /*2b*/String/*2c*/) extends AnyRef" transformsTo ("class A(/*2a*/s: /*2b*/String/*2c*//*1a*/, i:/*1b*/Int/*1c*/) extends AnyRef", reverseClassParameters.transform(_))
  }
  
  def testSortClassMembers() = {
    """
      class A { //the body:
        val a: Int /*a comment*/
        val b: String
        val c: Int
      }
    """ transformsTo( 
    """
      class A { //the body:
        val c: Int
        val b: String
        val a: Int /*a comment*/
      }
    """, 
      reverseClassParameters.transform(_))
  }
}

