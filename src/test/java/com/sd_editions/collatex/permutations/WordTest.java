package com.sd_editions.collatex.permutations;

import com.sd_editions.collatex.permutations.Word;

import junit.framework.TestCase;

public class WordTest extends TestCase {
  public void testNormalize1() {
    Word word = new Word("Hello,", 1);
    assertEquals("hello", word.normalized);
  }

  public void testNormalize2() {
    Word word = new Word("ειπων", 2);
    assertEquals("ειπων", word.normalized);
  }
}