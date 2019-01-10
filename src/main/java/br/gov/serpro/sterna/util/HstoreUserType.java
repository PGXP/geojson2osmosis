package br.gov.serpro.sterna.util;


import java.io.Serializable;
import static java.lang.String.format;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SERPRO
 */
public class HstoreUserType implements UserType {

    /**
     * PostgreSQL {@code hstore} field separator token.
     */
    private static final String HSTORE_SEPARATOR_TOKEN = "=>";

    /**
     * {@link Pattern} used to find and split {@code hstore} entries.
     */
    private static final Pattern HSTORE_ENTRY_PATTERN = compile(format("\"(.*)\"%s\"(.*)\"", HSTORE_SEPARATOR_TOKEN));

    /**
     * The PostgreSQL value for the {@code hstore} data type.
     */
    public static final int HSTORE_TYPE = 1_111;

    /**
     *
     * @return
     */
    @Override
    public int[] sqlTypes() {
        return new int[]{HSTORE_TYPE};
    }

    /**
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Class returnedClass() {
        return Map.class;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     * @throws HibernateException
     */
    @Override
    public boolean equals(final Object x, final Object y) throws HibernateException {
        return x.equals(y);
    }

    /**
     *
     * @param x
     * @return
     * @throws HibernateException
     */
    @Override
    public int hashCode(final Object x) throws HibernateException {
        return x.hashCode();
    }

    /**
     *
     * @param rs
     * @param strings
     * @param si
     * @param o
     * @return
     * @throws HibernateException
     * @throws SQLException
     */
    @Override
    public Object nullSafeGet(java.sql.ResultSet rs, String[] strings, SessionImplementor si, Object o)
            throws HibernateException, SQLException {
        return convertToEntityAttribute(rs.getString(strings[0]));
    }

    /**
     *
     * @param st
     * @param value
     * @param index
     * @param session
     * @throws HibernateException
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    @Override
    public void nullSafeSet(final PreparedStatement st, final Object value, final int index,
            final SessionImplementor session) throws HibernateException, SQLException {
        st.setObject(index, convertToDatabaseColumn((Map<String, Object>) value), HSTORE_TYPE);

    }

    /**
     *
     * @param value
     * @return
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object deepCopy(final Object value) throws HibernateException {
        return new HashMap<>(((Map<String, Object>) value));
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isMutable() {
        return true;
    }

    /**
     *
     * @param value
     * @return
     * @throws HibernateException
     */
    @Override
    public Serializable disassemble(final Object value) throws HibernateException {
        return (Serializable) value;
    }

    /**
     *
     * @param cached
     * @param owner
     * @return
     * @throws HibernateException
     */
    @Override
    public Object assemble(final Serializable cached, final Object owner)
            throws HibernateException {
        return cached;
    }

    /**
     *
     * @param original
     * @param target
     * @param owner
     * @return
     * @throws HibernateException
     */
    @Override
    public Object replace(final Object original, final Object target, final Object owner)
            throws HibernateException {
        return original;
    }

    private String convertToDatabaseColumn(final Map<String, Object> attribute) {
        final StringBuilder builder = new StringBuilder();
        attribute.entrySet().stream().map((entry) -> {
            if (builder.length() > 1) {
                builder.append(", ");
            }
            builder.append("\"");
            builder.append(entry.getKey());
            return entry;
        }).map((entry) -> {
            builder.append("\"");
            builder.append(HSTORE_SEPARATOR_TOKEN);
            builder.append("\"");
            builder.append(entry.getValue().toString());
            return entry;
        }).forEachOrdered((_item) -> {
            builder.append("\"");
        });
        return builder.toString();
    }

    private Map<String, Object> convertToEntityAttribute(final String dbData) {
        final Map<String, Object> data = new HashMap<>();
        final StringTokenizer tokenizer = new StringTokenizer(dbData, ",");

        while (tokenizer.hasMoreTokens()) {
            final Matcher matcher = HSTORE_ENTRY_PATTERN.matcher(tokenizer.nextToken().trim());
            if (matcher.find()) {
                data.put(matcher.group(1), matcher.group(2));
            }
        }

        return data;
    }
    private static final Logger LOG = Logger.getLogger(HstoreUserType.class.getName());

}
