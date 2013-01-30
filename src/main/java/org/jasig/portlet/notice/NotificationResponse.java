/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.portlet.notice;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class contains all the categories and errors
 * retrieved by an INotificationService. It is also
 * used to aggregate all the NotificationResponses from
 * various services into a single NotificationResponse.
 * The data from the overall NotificationResponse instance
 * is returned to the portlet to be rendered.
 */
public class NotificationResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<NotificationCategory> categories = new ArrayList<NotificationCategory>();
	private List<NotificationError> errors = new ArrayList<NotificationError>();

	public NotificationResponse(){}

	public NotificationResponse(
			List<NotificationCategory> categories,
			List<NotificationError> errors){
		this.categories = categories;
		this.errors = errors;
	}
	
	/**
	 * Set the source of the data. This method will iterate through the
	 * data and set the source value for the entries and error (if any).
	 * @param source is the source of the data.
	 */
	public void setSource(String source)
	{
		for(NotificationCategory category : categories)
			category.setSource(source);
		for(NotificationError error : errors)
			error.setSource(source);
	}

//	/**
//	 * Write the instance data to a JSON data String.
//	 *
//	 * @return String, null if the data is invalid.
//	 */
//	public String toJson()
//	{
//		return toJson(this);
//	}

//	/**
//	 * Write the instance data to a JSON data String.
//	 *
//	 * @return String, null if the data is invalid.
//	 */
//	public static String toJson(NotificationResponse request)
//	{
//		try
//		{
//			JSON json = JSONSerializer.toJSON(request.toMap());
//			return json.toString(1);
//		}
//		catch(JSONException je)
//		{
//			je.printStackTrace();
//			return null;
//		}
//	}
	
	public Map<String, Object> toMap()
	{
		Map<String, Object> map = new HashMap<String, Object>();

		for(int i = 0; i < categories.size(); i++)
			map.put("categories", categories);

		for(int j = 0; j < errors.size(); j++)
			map.put("errors", errors);

		return map;
	}

	/**
	 * Extract the category and error data from the given response and
	 * add it to this instance's data.
	 * @param response the source of data
	 */
	public void addResponseData(NotificationResponse response) {
    	addCategories(response.getCategories());
    	addErrors(response.getErrors());

	}

	public List<NotificationCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<NotificationCategory> categories) {
		this.categories = categories;
	}

	/** Insert the given categories and their entries into the any existing
	 * categories of the same title. If a category doesn't match an existing
	 * one, add it to the list.
	 * @param newCategories collection of new categories and their entries.
	 */
	public void addCategories(List<NotificationCategory> newCategories) {
		
		//check if an existing category (by the same title) already exists
		//if so, add the new categories entries to the existing category
		for(NotificationCategory newCategory : newCategories) {
			boolean found = false;

			for(NotificationCategory myCategory : categories) {
				if(myCategory.getTitle().toLowerCase().equals(newCategory.getTitle().toLowerCase())){
					found = true;
					myCategory.addEntries(newCategory.getEntries());
				}
			}
			
			if(!found)
				categories.add(newCategory);
		}
	}

	public void clearCategories() {
		categories.clear();
	}

	public List<NotificationError> getErrors() {
		return errors;
	}

	public void setErrors(List<NotificationError> errors) {
		this.errors = errors;
	}

	public void addErrors(List<NotificationError> newErrors) {
		for(NotificationError error : newErrors)
			errors.add(error);
	}

	public void filterErrors(Set<Integer> filterErrors) {
	    List<NotificationError> iSetClone = new ArrayList<NotificationError>(errors);
        for(NotificationError error : iSetClone)
        {
            if(filterErrors.contains(error.getKey()))
            {
                errors.remove(error);
            }   
        }
	}

	public void clearErrors() {
		errors.clear();
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer(
				"org.jasig.portlet.notice.serverresponse.NotificationRequest\n");

		for(NotificationCategory category : categories)
			buffer.append(category.toString());

		for(NotificationError error : errors)
			buffer.append(error.toString());

		return buffer.toString();
	}
}
