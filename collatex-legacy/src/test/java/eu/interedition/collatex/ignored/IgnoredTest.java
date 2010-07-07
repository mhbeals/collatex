/**
 * CollateX - a Java library for collating textual sources,
 * for example, to produce an apparatus.
 *
 * Copyright (C) 2010 ESF COST Action "Interedition".
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.interedition.collatex.ignored;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import eu.interedition.collatex.alignment.Alignment;
import eu.interedition.collatex.alignment.Match;
import eu.interedition.collatex.alignment.MatchSequence;
import eu.interedition.collatex.collation.CollateCore;
import eu.interedition.collatex.input.builders.WitnessBuilder;

public class IgnoredTest {
  private WitnessBuilder builder;

  @Before
  public void setUp() throws Exception {
    builder = new WitnessBuilder();
  }

  @Test
  @Ignore
  public void testGetAllMatchNonMatchPermutations() {
    CollateCore cc = new CollateCore(builder.buildWitnesses("The black car", "The dark car", "the dark day"));
    List<List<Alignment>> allMatchNonMatchPermutations = cc.getAllMatchNonMatchPermutations();
    // there should be 3 witness pairs
    assertEquals(3, allMatchNonMatchPermutations.size());
    List<Alignment> list = allMatchNonMatchPermutations.get(0);
    assertEquals(1, list.size());
    showAllMatchNonMatchPermutations(allMatchNonMatchPermutations);
  }

  @Test
  @Ignore
  public void testGetAllMatchNonMatchPermutations1() {
    CollateCore cc = new CollateCore(builder.buildWitnesses("The cat chases the dog and mouse", "The dog chases the cat and mouse", "the dog and mouse chase the cat"));
    List<List<Alignment>> allMatchNonMatchPermutations = cc.getAllMatchNonMatchPermutations();
    showAllMatchNonMatchPermutations(allMatchNonMatchPermutations);
    assertEquals(3, allMatchNonMatchPermutations.size());
  }

  void showAllMatchNonMatchPermutations(List<List<Alignment>> allMatchNonMatchPermutations) {
    for (List<Alignment> list : allMatchNonMatchPermutations) {
      println("witness pair " + allMatchNonMatchPermutations.indexOf(list));
      for (Alignment matchNonMatch : list) {
        println("  matchNonMatch " + list.indexOf(matchNonMatch));
        List<MatchSequence> matchSequencesForBase = matchNonMatch.getMatchSequencesOrderedForWitnessA();
        matchNonMatch.getGaps();
        for (MatchSequence matchSequence : matchSequencesForBase) {
          println("    matchSequenceForBase " + matchSequencesForBase.indexOf(matchSequence));
          List<Match> matches = matchSequence.getMatches();
          List<String> baseWords = Lists.newArrayList();
          List<String> witnessWords = Lists.newArrayList();
          for (Match match : matches) {
            baseWords.add(match.getBaseWord().getOriginal());
            witnessWords.add(match.getWitnessWord().getOriginal());
          }
          println("      '" + Joiner.on(" ").join(baseWords) + "' -> '" + Joiner.on(" ").join(witnessWords) + "'");
        }
      }
    }
  }

  private void println(String string) {
    System.out.println(string);
  }

  @Test
  @Ignore
  public void testSymmetry1() {
    symmetryTest("and then, the black cat and the black dog", "the black cat and the black dog");
  }

  @Test
  @Ignore
  public void testSymmetry2() {
    symmetryTest("the black cat and the black dog, and then", "the black cat and the black dog");
  }

  @Test
  @Ignore
  public void testSymmetry3() {
    symmetryTest("the black cat and the black dog", "the calico cat and the red dog");
  }

  private void symmetryTest(String string1, String string2) {
    new CollateCore(builder.build(string1), builder.build(string2));
    Assert.fail();
    //    Util.p("ORIGINAL");
    //    List<Modifications> permutations = colors.compareWitness(1, 2);
    //    //    Util.p("MIRROR");
    //    List<Modifications> mirrorPermutations = colors.compareWitness(2, 1);
    //    assertEquals(permutations.size(), mirrorPermutations.size());
  }

}
