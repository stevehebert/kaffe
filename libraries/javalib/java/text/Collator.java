/*
 * Java core library component.
 *
 * Copyright (c) 1997, 1998
 *      Transvirtual Technologies, Inc.  All rights reserved.
 *
 * See the file "license.terms" for information on usage and redistribution
 * of this file.
 */

package java.text;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

/* NB: Collator is serializable only in JDK 1.1, not in JDK 1.2 */
public abstract class Collator implements Comparator, Cloneable, Serializable {

public final static int NO_DECOMPOSITION = 0;
public final static int CANONICAL_DECOMPOSITION = 1;
public final static int FULL_DECOMPOSITION = 2;
public final static int IDENTICAL = 3;
public final static int PRIMARY = 0;
public final static int SECONDARY = 1;
public final static int TERTIARY = 2;

/* locales in kaffe.text.collator */
private static final Locale [] LOCALES = new Locale[] {
	Locale.US
};

private int mode;
private int strength;

protected Collator() {
	mode = NO_DECOMPOSITION;
	strength = PRIMARY;
}

public Object clone() {
	try {
		return (super.clone());
	}
	catch (CloneNotSupportedException _) {
		return (null);
	}
}

public abstract int compare(String src, String target);

public int compare(Object o1, Object o2) {
	return compare((String)o1, (String)o2);
}

public boolean equals(Object obj) {
	if (obj instanceof Collator) {
		Collator other = (Collator)obj;
		if (mode == other.mode && strength == other.strength) {
			return (true);
		}
	}
	return (false);
}

public boolean equals(String src, String target) {
	return compare(src, target) == 0;
}

public static synchronized Locale[] getAvailableLocales() {
        return (LOCALES);
}

public abstract CollationKey getCollationKey(String src);

public synchronized int getDecomposition() {
	return (mode);
}

public static synchronized Collator getInstance() {
	return (getInstance(Locale.getDefault()));
}

public static synchronized Collator getInstance(Locale loc) {
        ResourceBundle bundle = Format.getResources("collator", loc);
	try {
	        return (new RuleBasedCollator(bundle.getString("rule")));
	}
	catch (ParseException _) {
	        //is this the right thing to do?
	        return null;
	}
}

public synchronized int getStrength() {
	return (strength);
}

public abstract int hashCode();

public synchronized void setDecomposition(int mode) {
	switch (mode) {
	case NO_DECOMPOSITION:
	case CANONICAL_DECOMPOSITION:
	case FULL_DECOMPOSITION:
		break;
	default:
		throw new IllegalArgumentException();
	}
	this.mode = mode;
}

public synchronized void setStrength(int strength) {
	switch (strength) {
	case PRIMARY:
	case SECONDARY:
	case TERTIARY:
	case IDENTICAL:
		break;
	default:
		throw new IllegalArgumentException();
	}
	this.strength = strength;
}

}
