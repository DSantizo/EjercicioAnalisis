/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import fr.mcc.ginco.beans.NoteType;
import fr.mcc.ginco.dao.INoteTypeDAO;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Dennis Santizo
 */
@Repository
public class NoteTypeDAO extends GenericHibernateDAO<NoteType, String>
		implements INoteTypeDAO {

	public NoteTypeDAO() {
		super(NoteType.class);
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.INoteTypeDAO#findConceptNoteTypes()
	 */
	@Override
	public List<NoteType> findConceptNoteTypes() {
		return getCurrentSession().createCriteria(NoteType.class).add(Restrictions.eq("isConcept", true)).list();
	}


	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.INoteTypeDAO#findTermNoteTypes()
	 */
	@Override
	public List<NoteType> findTermNoteTypes() {
		return getCurrentSession().createCriteria(NoteType.class).add(Restrictions.eq("isTerm", true)).list();
	}
}
