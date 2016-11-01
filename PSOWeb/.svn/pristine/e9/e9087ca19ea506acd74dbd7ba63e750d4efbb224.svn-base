package com.itelasoft.util;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import com.itelasoft.pso.beans.EnumFileType;
public class EnumUserType<E extends Enum<E>> implements UserType { 

	private Class<E> clazz = null; 
    @Override
    public Object nullSafeGet(ResultSet arg0, String[] arg1,
    		SessionImplementor arg2, Object arg3) throws HibernateException,
    		SQLException {
    	// TODO Auto-generated method stub
    	return null;
    }
    
    @Override
    public void nullSafeSet(PreparedStatement arg0, Object arg1, int arg2,
    		SessionImplementor arg3) throws HibernateException, SQLException {
    	// TODO Auto-generated method stub
    	
    }
    protected EnumUserType(Class<E> c) { 
        this.clazz = c; 
    } 
 
    private static final int[] SQL_TYPES = {Types.VARCHAR}; 
    public int[] sqlTypes() { 
        return SQL_TYPES; 
    } 
 
    public Class returnedClass() { 
        return clazz; 
    } 
 
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException { 
        String name = resultSet.getString(names[0]); 
        E result = null; 
        if (!resultSet.wasNull()) { 
            result = Enum.valueOf(clazz, name); 
        } 
        return result; 
    } 
 
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException { 
        if (null == value) { 
            preparedStatement.setNull(index, Types.VARCHAR); 
        } else { 
            preparedStatement.setString(index, ((EnumFileType)value).getId()); 
        } 
    } 
 
    public Object deepCopy(Object value) throws HibernateException{ 
        return value; 
    } 
 
    public boolean isMutable() { 
        return false; 
    } 
 
    public Object assemble(Serializable cached, Object owner) throws HibernateException  {
         return cached;
    } 

    public Serializable disassemble(Object value) throws HibernateException { 
        return (Serializable)value; 
    } 
 
    public Object replace(Object original, Object target, Object owner) throws HibernateException { 
        return original; 
    } 
    public int hashCode(Object x) throws HibernateException { 
        return x.hashCode(); 
    } 
    public boolean equals(Object x, Object y) throws HibernateException { 
        if (x == y) 
            return true; 
        if (null == x || null == y) 
            return false; 
        return x.equals(y); 
    } 
} 