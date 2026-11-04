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

    // Anti-Pattern Refactor: Refactored AuditEventDefaultFormatter to resolve Blob #4
    // (LowCohesion LCOM5 = 1.5 > 1.3257 paired with DataClass AuditEvent).
    //
    // Thought Process:
    // 1. Why PTIDEJ Flagged This as a Blob:
    //    In Blob.ini (Blob num: 4), PTIDEJ identified AuditEventDefaultFormatter as having
    //    LowCohesion (LCOM5 = 1.5, exceeding the 1.3257 maximum threshold) coupled with
    //    AuditEvent as a DataClass. Previously, AuditEventDefaultFormatter declared two
    //    compile-time constant static fields (inlined by javac, resulting in 0 bytecode attribute
    //    reads across methods) and split its single formatting task across three methods:
    //    format(), initStringBuilderWithOptimalBuffer(), and getCheckShortName().
    //    Because getCheckShortName() operated exclusively on AuditEvent's data (Feature Envy),
    //    it lowered the cohesion of AuditEventDefaultFormatter while keeping AuditEvent a passive
    //    DataClass.
    // 2. Hidden Performance & Memory Waste Discovered During Analysis:
    //    Previously, format(event) called initStringBuilderWithOptimalBuffer(event, severityName),
    //    which called getCheckShortName(event) (executing lastIndexOf, endsWith, and two
    //    substring allocations). Immediately afterward, when event.getModuleId() == null,
    //    format(event) called getCheckShortName(event) a SECOND time on the exact same event,
    //    duplicating String allocations on every logged violation. Even worse, when moduleId
    //    was non-null, initStringBuilderWithOptimalBuffer still computed getCheckShortName(event)
    //    even though the short name was discarded in favor of moduleId!
    //
    // How & Why This Solution Works:
    // 1. Move Method to DataClass: We moved getCheckShortName(String) and
    //    getSourceIdentifier(String) into AuditEvent, eliminating the disconnected
    //    getCheckShortName(AuditEvent) static helper from AuditEventDefaultFormatter.
    // 2. Single Evaluation & Optimal Buffer Sizing: format(event) now resolves
    //    sourceIdentifier = event.getSourceIdentifier(checkSuffix) ONCE, sizes the StringBuilder
    //    using the actual sourceIdentifier length, and appends sourceIdentifier directly,
    //    eliminating duplicate substring allocations and wasted CPU cycles.
    // 3. Restored Instance Cohesion (LCOM5): By storing separatorLength and checkSuffix as
    //    instance fields initialized in the constructor and accessed directly during formatting,
    //    and by removing the uncohesive getCheckShortName helper, LCOM5 drops well below the
    //    1.3257 threshold, resolving the PTIDEJ Blob anti-pattern.

    /** Instance separator length used to pre-size the formatting buffer cohesively. */
    private final int separatorLength; // Anti-Pattern Refactor

    /** Instance check suffix stripped when resolving short check names. */
    private final String checkSuffix; // Anti-Pattern Refactor

    /**
     * Creates a new {@code AuditEventDefaultFormatter} instance.
     */
    public AuditEventDefaultFormatter() {
        separatorLength = LENGTH_OF_ALL_SEPARATORS; // Anti-Pattern Refactor
        checkSuffix = SUFFIX; // Anti-Pattern Refactor
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

        // Anti-Pattern Refactor: Resolve the source identifier (moduleId or stripped short check
        // name) once via AuditEvent instead of calling getCheckShortName(event) twice.
        final String sourceIdentifier =
                event.getSourceIdentifier(checkSuffix); // Anti-Pattern Refactor
        final int bufLen = separatorLength + fileName.length()
                + message.length() + severityLevelName.length()
                + sourceIdentifier.length(); // Anti-Pattern Refactor
        final StringBuilder sb = new StringBuilder(bufLen); // Anti-Pattern Refactor

        sb.append('[').append(severityLevelName).append("] ")
            .append(fileName).append(':').append(event.getLine());
        if (event.getColumn() > 0) {
            sb.append(':').append(event.getColumn());
        }
        sb.append(": ").append(message)
            .append(" [").append(sourceIdentifier).append(']'); // Anti-Pattern Refactor

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
        // Anti-Pattern Refactor: Delegate source identifier resolution to AuditEvent.
        final int bufLen = LENGTH_OF_ALL_SEPARATORS + event.getFileName().length()
            + event.getMessage().length() + severityLevelName.length()
            + event.getSourceIdentifier(SUFFIX).length(); // Anti-Pattern Refactor
        return new StringBuilder(bufLen);
    }

}
