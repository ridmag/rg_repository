package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.Collection;

@ManagedBean
@SessionScoped
public class CollectionManagerModel extends UIModel {
	private List<Collection> collections;
	private Collection collection, coll;
	private String collectionName;

	public CollectionManagerModel() {
		init();
	}

	public void init() {
		collectionName = "";
		collections = serviceLocator.getCollectionService().findAll();
		collection = null;
		if (collections == null)
			collections = new ArrayList<Collection>();
		collection = coll = null;
	}

	public void selectCollection(ClickActionEvent ce) {
		if (coll != null)
			coll.setUi_selected(false);
		coll = (Collection) ce.getComponent().getAttributes().get("c");
		coll.setUi_selected(true);
		collection = serviceLocator.getCollectionService().retrieve(
				coll.getId());
	}

	public void newCollection() {
		if (coll != null)
			coll.setUi_selected(false);
		collection = new Collection();
	}

	public void searchCollection() {
		List<Collection> collections = serviceLocator.getCollectionService()
				.searchByName(collectionName);
		if (collections == null || collections.isEmpty()) {
			showError("No results found for the given given text.");
		}
		this.collections = collections;

	}

	public void deleteCollection() {
		try {
			serviceLocator.getCollectionService().delete(collection.getId());
			collections.remove(coll);
			collection = null;
			showInfo("Collection deleted successfully.");
		} catch (DataIntegrityViolationException e) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void saveCollection() {
		if (validateFields()) {
			if (collection.getId() == null) {
				collection = serviceLocator.getCollectionService().create(
						collection);
				collection.setUi_selected(true);
				coll = collection;
				collections.add(coll);
				collection = serviceLocator.getCollectionService().retrieve(
						coll.getId());
				showInfo("New collection created successfully.");
			} else {
				collection = serviceLocator.getCollectionService().update(
						collection);
				collection.setUi_selected(true);
				collections.set(collections.indexOf(coll), collection);
				coll = collection;
				collection = serviceLocator.getCollectionService().retrieve(
						coll.getId());
				showInfo("Collection updated successfully.");
			}
		}
	}

	private boolean validateFields() {
		if (!validateString(collection.getName())) {
			showError("Collection name cannot be empty..");
			return false;
		}
		if (collection.getAmount() <= 0) {
			showError("Collection amount is invalid..");
			return false;
		}
		if (!validateString(collection.getDescription())) {
			showError("Please provide a description..");
			return false;
		}
		if (collection.getStartDate() == null) {
			showError("Collection start date cannot be empty..");
			return false;
		}
		if (collection.getEndDate() == null
				|| collection.getEndDate().equals(collection.getStartDate())) {
			collection.setEndDate(collection.getStartDate());
		}
		if (collection.getEndDate().before(collection.getStartDate())) {
			showError("End date cannot be before start date..");
			return false;
		} else {
			return true;
		}
	}

	private boolean validateString(String string) {
		if (string != null && !string.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}

	public Collection getCollection() {
		return collection;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public Collection getColl() {
		return coll;
	}

	public void setColl(Collection coll) {
		this.coll = coll;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
}
