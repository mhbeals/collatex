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

package eu.interedition.collatex.alignment;

import eu.interedition.collatex.experimental.ngrams.alignment.Addition;
import eu.interedition.collatex.experimental.ngrams.alignment.Modification;
import eu.interedition.collatex.experimental.ngrams.alignment.Omission;
import eu.interedition.collatex.experimental.ngrams.alignment.Replacement;
import eu.interedition.collatex.input.BaseContainerPart;
import eu.interedition.collatex.input.BaseElement;

public class Gap<T extends BaseElement> {
  final BaseContainerPart<T> _partA;
  final BaseContainerPart<T> _partB;
  final Match<T> next;

  public Gap(final BaseContainerPart<T> partA, final BaseContainerPart<T> partB, final Match<T> _next) {
    this._partA = partA;
    this._partB = partB;
    this.next = _next;
  }

  //TODO rename method to getPartA
  public BaseContainerPart<T> getPhraseA() {
    return _partA;
  }

  //TODO rename method to getPartB
  public BaseContainerPart<T> getPhraseB() {
    return _partB;
  }

  public Addition createAddition() {
    throw new UnsupportedOperationException("Not implemented on this class! See the new Gap class!");
    //return new Addition(_partA.getBeginPosition(), _partB);
  }

  public Omission createOmission() {
    throw new UnsupportedOperationException("Not implemented on this class! See the new Gap class!");
    // return new Omission(_partA);
  }

  public Replacement createReplacement() {
    throw new UnsupportedOperationException("Not implemented on this class! See the new Gap class!");
    // return new Replacement(_partA, _partB);
  }

  public boolean isAddition() {
    return !_partA.hasGap() && _partB.hasGap();
  }

  public boolean isOmission() {
    return _partA.hasGap() && !_partB.hasGap();
  }

  public boolean isReplacement() {
    return _partA.hasGap() && _partB.hasGap();
  }

  public boolean isValid() {
    return _partA.hasGap() || _partB.hasGap();
  }

  @Override
  public String toString() {
    String result = "NonMatch: addition: " + isAddition() + " base: " + _partA;
    if (isAtTheEnd()) {
      result += "; nextWord: none";
    } else {
      result += "; nextWord: " + getNextMatch().getBaseWord();
    }
    result += "; witness: " + _partB;
    return result;
  }

  public Modification analyse() {
    if (isAddition()) {
      return createAddition();
    }
    if (isOmission()) {
      return createOmission();
    }
    if (isReplacement()) {
      return createReplacement();
    }
    throw new RuntimeException("Not a modification!");
  }

  // Note: this the next match after the gap for the second witness!
  public Match<T> getNextMatch() {
    if (next == null) {
      throw new RuntimeException("There is no next match!");
    }
    return next;
  }

  public boolean isAtTheEnd() {
    return next == null;
  }

}
