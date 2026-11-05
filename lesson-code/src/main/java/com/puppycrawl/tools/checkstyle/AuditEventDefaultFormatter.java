///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Represents the default formatter for log message.
 * Default log message format is:
 * [SEVERITY LEVEL] filePath:lineNo:columnNo: message. [CheckName]
 * When the module id of the message has been set, the format is:
 * [SEVERITY LEVEL] filePath:lineNo:columnNo: message. [ModuleId]
 */
public class AuditEventDefaultFormatter implements AuditEventFormatter {

    /** Length of all separators. */
    private static final int LENGTH_OF_ALL_SEPARATORS = 10;

    /** Suffix of module names like FooCheck. */
    private static final String SUFFIX = "Check";

    /** Instance separator length used to pre-size the formatting buffer cohesively. */
    private final int separatorLength;

    /** Instance check suffix stripped when resolving short check names. */
    private final String checkSuffix;

    /**
     * Creates a new {@code AuditEventDefaultFormatter} instance.
     */
    public AuditEventDefaultFormatter() {
        separatorLength = LENGTH_OF_ALL_SEPARATORS;
        checkSuffix = SUFFIX;
    }

    @Override
    public String format(AuditEvent event) {
        final String fileName = event.getFileName();
        final String message = event.getMessage();

        final SeverityLevel severityLevel = event.getSeverityLevel();
        final String severityLevelName;
        if (severityLevel == SeverityLevel.WARNING) {
            // We change the name of severity level intentionally
            // to shorten the length of the log message.
            severityLevelName = "WARN";
        }
        else {
            severityLevelName = severityLevel.getName().toUpperCase(Locale.US);
        }

        final String sourceIdentifier = event.getSourceIdentifier(checkSuffix);
        final int bufLen = separatorLength + fileName.length()
                + message.length() + severityLevelName.length()
                + sourceIdentifier.length();
        final StringBuilder sb = new StringBuilder(bufLen);

        sb.append('[').append(severityLevelName).append("] ")
            .append(fileName).append(':').append(event.getLine());
        if (event.getColumn() > 0) {
            sb.append(':').append(event.getColumn());
        }
        sb.append(": ").append(message)
            .append(" [").append(sourceIdentifier).append(']');

        return sb.toString();
    }

    /**
     * Returns the StringBuilder that should avoid StringBuffer.expandCapacity.
     * bufferLength = fileNameLength + messageLength + lengthOfAllSeparators +
     * + severityNameLength + checkNameLength.
     * Method is excluded from pitest validation.
     *
     * @param event audit event.
     * @param severityLevelName severity level name.
     * @return optimal StringBuilder.
     */
    private static StringBuilder initStringBuilderWithOptimalBuffer(AuditEvent event,
            String severityLevelName) {
        final int bufLen = LENGTH_OF_ALL_SEPARATORS + event.getFileName().length()
            + event.getMessage().length() + severityLevelName.length()
            + event.getSourceIdentifier(SUFFIX).length();
        return new StringBuilder(bufLen);
    }

}
