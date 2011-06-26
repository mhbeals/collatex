/**
 * Layered Markup and Annotation Language for Java (lmnl4j):
 * implementation of LMNL, a markup language supporting layered and/or
 * overlapping annotations.
 *
 * Copyright (C) 2010 the respective authors.
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

package eu.interedition.text.rdbms;

import com.google.common.base.Objects;
import com.google.common.collect.Ordering;
import eu.interedition.text.Annotation;
import eu.interedition.text.QName;
import eu.interedition.text.Range;

import java.io.Serializable;

public class AnnotationRelation implements Annotation {
  protected int id;
  protected QName name;
  protected TextRelation text;
  protected Range range;
  protected Serializable serializableData;

  public AnnotationRelation() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public QName getName() {
    return name;
  }

  public void setName(QName name) {
    this.name = name;
  }

  public TextRelation getText() {
    return text;
  }

  public void setText(TextRelation text) {
    this.text = text;
  }

  public Range getRange() {
    return range;
  }

  public void setRange(Range range) {
    this.range = range;
  }

  public Object getData() {
    return serializableData;
  }

  public Serializable getSerializableData() {
    return serializableData;
  }

  public void setSerializableData(Serializable serializableData) {
    this.serializableData = serializableData;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).addValue(getName()).addValue(getRange()).toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (id != 0 && obj != null && obj instanceof AnnotationRelation) {
      return id == ((AnnotationRelation) obj).id;
    }
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return (id == 0 ? super.hashCode() : id);
  }

  // FIXME: implement proper type-safe removal
  // public void remove(LmnlRange deleted) {
  // remove(deleted, true);
  // }
  //
  // protected void remove(LmnlRange deleted, boolean effectedLayer) {
  // for (Iterator<LmnlAnnotation> it = annotations.iterator();
  // it.hasNext();) {
  // LmnlAnnotation child = it.next();
  // if (!(child instanceof LmnlRange)) {
  // child.remove(deleted, false);
  // continue;
  // }
  // LmnlRange range = (LmnlRange) child;
  // if (range.congruentWith(deleted) || deleted.encloses(range)) {
  // it.remove();
  // continue;
  // }
  // if (deleted.overlapsWith(range)) {
  // child.remove(range.overlap(deleted).relativeTo(range), false);
  // }
  // child.setRange(range.substract(deleted));
  // }
  // if (effectedLayer && text != null) {
  // int length = text.length();
  // this.text = text.substring(0, Math.min(length, deleted.getStart())) +
  // text.substring(Math.min(deleted.getEnd(), length), length);
  // }
  // }

  public int compareTo(Annotation o) {
    final int rangeComparison = range.compareTo(o.getRange());
    if (rangeComparison != 0) {
      return rangeComparison;
    }
    final int nameComparison = name.compareTo(o.getName());
    if (nameComparison != 0) {
      return nameComparison;
    }
    return Ordering.arbitrary().compare(this, o);
  }
}
