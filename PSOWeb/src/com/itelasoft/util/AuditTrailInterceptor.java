package com.itelasoft.util;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;


class AuditTrailInterceptor extends EmptyInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, org.hibernate.type.Type[] types) {
		
		if (entity instanceof Auditable)
			System.out.println("flushDirty" + UserUtils.getCurrentUsername()
					+ "entity:" + ((IAuditLog) entity).getLogDeatil());
		createLog(entity,"update",currentState,propertyNames,previousState);
		setValue(currentState, propertyNames, "updatedBy",
				UserUtils.getCurrentUsername());
		setValue(currentState, propertyNames, "updatedOn", new Date());
		return true;
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, org.hibernate.type.Type[] types) {
		createLog(entity,"create",state,propertyNames,null);
		setValue(state, propertyNames, "createdBy",
				UserUtils.getCurrentUsername());
		setValue(state, propertyNames, "createdOn", new Date());
		return true;
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		createLog(entity,"delete",state,propertyNames,null);
		// TODO Auto-generated method stub
		super.onDelete(entity, id, state, propertyNames, types);
	}

	@Override
	public void onCollectionUpdate(Object collection, Serializable key)
			throws CallbackException {
		System.out.println("onCollectionUpdate");
		super.onCollectionUpdate(collection, key);
	}
	private void setValue(Object[] currentState, String[] propertyNames,
			String propertyToSet, Object value) {
		
		int index = ArrayUtils.indexOf(propertyNames, propertyToSet);
		if (index >= 0) {
			System.out.println("saving:" + propertyToSet + ":" + value);
			currentState[index] = value;
		}
	}
	
	private void createLog(Object entity,String type,Object[] currentState, String[] propertyNames,Object[] previousStates) {
		int i = 0;
		StringBuilder builder = new StringBuilder();
		builder.append(type).append(entity.getClass().getCanonicalName()).append(":");
		
		for (String p : propertyNames) {
			builder.append(p).append(":").append(currentState[i++]).append(" , ");
		}
		System.out.println(builder.toString());
	}

}
