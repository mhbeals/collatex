package eu.interedition.collatex2.matching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Join;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.interedition.collatex2.implementation.Factory;
import eu.interedition.collatex2.implementation.alignmenttable.AlignmentTableCreator3;
import eu.interedition.collatex2.implementation.matching.IndexMatcher;
import eu.interedition.collatex2.interfaces.IAlignment;
import eu.interedition.collatex2.interfaces.IAlignmentTable;
import eu.interedition.collatex2.interfaces.IColumns;
import eu.interedition.collatex2.interfaces.IMatch;
import eu.interedition.collatex2.interfaces.IWitness;

public class IndexMatcherTest {
  private static Factory factory;

  @BeforeClass
  public static void setup() {
    factory = new Factory();
  }

  @Test
  public void testEverythingIsUnique() {
    final IWitness witnessA = factory.createWitness("A", "everything is unique should be no problem");
    final IWitness witnessB = factory.createWitness("B", "everything is unique");
    final IAlignmentTable table = AlignmentTableCreator3.createAlignmentTable(Lists.newArrayList(witnessA), Factory.NULLCALLBACK);
    final IAlignment alignment = factory.createAlignmentUsingIndex(table, witnessB);
    final List<IMatch> matches = alignment.getMatches();
    assertEquals(1, matches.size());
    final IMatch match = matches.get(0);
    assertEquals("everything is unique", match.getNormalized());
    final IColumns columnsA = match.getColumns();
    assertEquals(1, columnsA.getBeginPosition());
    assertEquals(3, columnsA.getEndPosition());
  }

  @Test
  public void testEverythingIsUniqueTwoWitnesses() {
    final IWitness witnessA = factory.createWitness("A", "everything is unique should be no problem");
    final IWitness witnessB = factory.createWitness("B", "this one very different");
    final IWitness witnessC = factory.createWitness("C", "everything is different");
    final IAlignmentTable table = AlignmentTableCreator3.createAlignmentTable(Lists.newArrayList(witnessA, witnessB), Factory.NULLCALLBACK);
    final List<IMatch> matches = IndexMatcher.getMatchesUsingWitnessIndex(table, witnessC);
    assertEquals(3, matches.size());
    final IMatch match = matches.get(0);
    assertEquals("everything", match.getNormalized());
    final IColumns columnsA = match.getColumns();
    assertEquals(1, columnsA.getBeginPosition());
    assertEquals(1, columnsA.getEndPosition());
    final IMatch match2 = matches.get(1);
    assertEquals("is", match2.getNormalized());
    final IColumns columnsB = match2.getColumns();
    assertEquals(2, columnsB.getBeginPosition());
    assertEquals(2, columnsB.getEndPosition());
    final IMatch match3 = matches.get(2);
    assertEquals("different", match3.getNormalized());
    final IColumns columnsC = match3.getColumns();
    assertEquals(4, columnsC.getBeginPosition());
    assertEquals(4, columnsC.getEndPosition());
  }

  @Test
  public void testOverlappingMatches() {
    final IWitness witnessA = factory.createWitness("A", "everything is unique should be no problem");
    final IWitness witnessB = factory.createWitness("B", "this one is different");
    final IWitness witnessC = factory.createWitness("C", "everything is different");
    final IAlignmentTable table = AlignmentTableCreator3.createAlignmentTable(Lists.newArrayList(witnessA, witnessB), Factory.NULLCALLBACK);
    final List<IMatch> matches = IndexMatcher.getMatchesUsingWitnessIndex(table, witnessC);
    assertEquals(3, matches.size());
    final IMatch match = matches.get(0);
    assertEquals("everything", match.getNormalized());
    final IColumns columnsA = match.getColumns();
    assertEquals(1, columnsA.getBeginPosition());
    assertEquals(1, columnsA.getEndPosition());
    final IMatch match2 = matches.get(1);
    assertEquals("is", match2.getNormalized());
    final IColumns columnsB = match2.getColumns();
    assertEquals(3, columnsB.getBeginPosition());
    assertEquals(3, columnsB.getEndPosition());
    final IMatch match3 = matches.get(2);
    assertEquals("different", match3.getNormalized());
    final IColumns columnsC = match3.getColumns();
    assertEquals(4, columnsC.getBeginPosition());
    assertEquals(4, columnsC.getEndPosition());
  }

  @Test
  public void testGetMatchesUsingWitnessIndex() {
    final IWitness witnessA = factory.createWitness("A", "The big black cat and the big black rat");
    final IWitness witnessB = factory.createWitness("B", "The big black");
    final IAlignmentTable table = AlignmentTableCreator3.createAlignmentTable(Lists.newArrayList(witnessA), Factory.NULLCALLBACK);
    final IAlignment alignment = factory.createAlignmentUsingIndex(table, witnessB);
    final List<IMatch> matches = alignment.getMatches();
    assertEquals(1, matches.size());
    final IMatch match = matches.get(0);
    assertEquals("the big black", match.getNormalized());
    final IColumns columnsA = match.getColumns();
    assertEquals(1, columnsA.getBeginPosition());
    assertEquals(3, columnsA.getEndPosition());
  }

  //Note: internally this gives # the big black and the big black cat as matches
  @Test
  public void testGetMatchesUsingWitnessIndexWithOverlapping() {
    final IWitness witnessA = factory.createWitness("A", "the big black cat and the big black rat");
    final IWitness witnessB = factory.createWitness("B", "the big black cat");
    final IAlignmentTable table = AlignmentTableCreator3.createAlignmentTable(Lists.newArrayList(witnessA), Factory.NULLCALLBACK);
    final IAlignment alignment = factory.createAlignmentUsingIndex(table, witnessB);
    final List<IMatch> matches = alignment.getMatches();
    //    final List<IMatch> matches = Factory.getMatchesUsingWitnessIndex(table, witnessB, new NormalizedLevenshtein());
    assertEquals(1, matches.size());
    final IMatch match = matches.get(0);
    assertEquals("the big black cat", match.getNormalized());
    final IColumns columnsA = match.getColumns();
    assertEquals(1, columnsA.getBeginPosition());
    assertEquals(4, columnsA.getEndPosition());
  }

  //TODO: make convenience method for creation of AlignmentTable on Factory!

  @Test
  public void testOverlappingMatches2() {
    final IWitness witnessA = factory.createWitness("A", "the black cat and the black mat");
    final IWitness witnessB = factory.createWitness("B", "the black dog and the black mat");
    final IAlignmentTable table = AlignmentTableCreator3.createAlignmentTable(Lists.newArrayList(witnessA), Factory.NULLCALLBACK);
    final IAlignment alignment = factory.createAlignmentUsingIndex(table, witnessB);
    final List<IMatch> matches = alignment.getMatches();
    assertEquals(2, matches.size());
    final IMatch match = matches.get(0);
    assertEquals("the black", match.getNormalized());
    //    final IColumns columnsA = match.getColumnsA();
    //    assertEquals(1, columnsA.getBeginPosition());
    //    assertEquals(4, columnsA.getEndPosition());
  }

  //NOTE: joining is already tested in other tests!
  @Ignore
  @Test
  public void testJoinOverlappingMatches() {
    // TODO make this testcase
    final List<IMatch> matches = Lists.newArrayList();
    final List<IMatch> joined = IndexMatcher.joinOverlappingMatches(matches);
    assertEquals(1, joined.size());
  }

  @Test
  public void testMatchesWithIndex() {
    final IWitness a = factory.createWitness("A", "The black cat");
    final IWitness b = factory.createWitness("B", "The black and white cat");
    final IAlignmentTable table = AlignmentTableCreator3.createAlignmentTable(Lists.newArrayList(a), Factory.NULLCALLBACK);
    final IAlignment alignment = factory.createAlignmentUsingIndex(table, b);
    final List<IMatch> matches = alignment.getMatches();
    assertContains(matches, "the black");
    assertContains(matches, "cat");
    assertEquals(2, matches.size());
  }

  final Function<IMatch, String> function = new Function<IMatch, String>() {
    @Override
    public String apply(final IMatch match) {
      return match.getNormalized();
    }
  };

  private void assertContains(final List<IMatch> matches, final String string) {
    final Iterable<String> normalizedMatches = Iterables.transform(matches, function);
    assertTrue(string + " not found in matches: " + Join.join(",", normalizedMatches), Lists.newArrayList(normalizedMatches).contains(string));
  }

}