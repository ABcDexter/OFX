/**
 * *********************************************************************
 *
 * Copyright (C) 2014 Avaneesh Rastogi (rastogi.avaneesh (at) gmail (dot) com)
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see (http://www.gnu.org/licenses/).
 *
 **********************************************************************
 */
package Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class RegexFilter extends DocumentFilter {

    private Pattern pattern;
//------------------------------------------------------------------------------

    public RegexFilter(String pat) {
        pattern = Pattern.compile(pat);
    }
//------------------------------------------------------------------------------

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {

        String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
        Matcher m = pattern.matcher(newStr);
        if (m.matches()) {
            super.insertString(fb, offset, string, attr);
        } else {
        }
    }
//------------------------------------------------------------------------------

    @Override
    public void replace(FilterBypass fb, int offset,
            int length, String string, AttributeSet attr) throws
            BadLocationException {

        if (length > 0) {
            fb.remove(offset, length);
        }
        insertString(fb, offset, string, attr);
    }
//------------------------------------------------------------------------------
}
