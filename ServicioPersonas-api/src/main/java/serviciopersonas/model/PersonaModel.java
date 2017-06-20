/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package serviciopersonas.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

/**
 * The base model interface for the Persona service. Represents a row in the &quot;FOO_Persona&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link serviciopersonas.model.impl.PersonaModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link serviciopersonas.model.impl.PersonaImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Persona
 * @see serviciopersonas.model.impl.PersonaImpl
 * @see serviciopersonas.model.impl.PersonaModelImpl
 * @generated
 */
@ProviderType
public interface PersonaModel extends BaseModel<Persona> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a persona model instance should use the {@link Persona} interface instead.
	 */

	/**
	 * Returns the primary key of this persona.
	 *
	 * @return the primary key of this persona
	 */
	public String getPrimaryKey();

	/**
	 * Sets the primary key of this persona.
	 *
	 * @param primaryKey the primary key of this persona
	 */
	public void setPrimaryKey(String primaryKey);

	/**
	 * Returns the dni of this persona.
	 *
	 * @return the dni of this persona
	 */
	@AutoEscape
	public String getDni();

	/**
	 * Sets the dni of this persona.
	 *
	 * @param dni the dni of this persona
	 */
	public void setDni(String dni);

	/**
	 * Returns the nombre of this persona.
	 *
	 * @return the nombre of this persona
	 */
	@AutoEscape
	public String getNombre();

	/**
	 * Sets the nombre of this persona.
	 *
	 * @param nombre the nombre of this persona
	 */
	public void setNombre(String nombre);

	/**
	 * Returns the edad of this persona.
	 *
	 * @return the edad of this persona
	 */
	public int getEdad();

	/**
	 * Sets the edad of this persona.
	 *
	 * @param edad the edad of this persona
	 */
	public void setEdad(int edad);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(serviciopersonas.model.Persona persona);

	@Override
	public int hashCode();

	@Override
	public CacheModel<serviciopersonas.model.Persona> toCacheModel();

	@Override
	public serviciopersonas.model.Persona toEscapedModel();

	@Override
	public serviciopersonas.model.Persona toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}