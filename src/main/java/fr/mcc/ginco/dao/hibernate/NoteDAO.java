/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import fr.mcc.ginco.beans.Note;
import fr.mcc.ginco.beans.ThesaurusConcept;
import fr.mcc.ginco.beans.ThesaurusTerm;
import fr.mcc.ginco.dao.INoteDAO;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Dennis Santizo
 */
@Repository
public class NoteDAO extends GenericHibernateDAO<Note, String>
		implements INoteDAO {

	public NoteDAO() {
		super(Note.class);
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.INoteDAO#findConceptNotes(java.lang.String)
	 */
	@Override
	public List<Note> findConceptPaginatedNotes(String conceptId, Integer startIndex, Integer limit) {
		return getCurrentSession().createCriteria(Note.class)
				.setMaxResults(limit)
				.add(Restrictions.eq("concept.identifier", conceptId))
				.setFirstResult(startIndex).addOrder(Order.asc("lexicalValue"))
				.list();
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.INoteDAO#findTermNotes(java.lang.String)
	 */
	@Override
	public List<Note> findTermPaginatedNotes(String termId, Integer startIndex, Integer limit) {
		return getCurrentSession().createCriteria(Note.class)
				.setMaxResults(limit)
				.add(Restrictions.eq("term.identifier", termId))
				.setFirstResult(startIndex).addOrder(Order.asc("lexicalValue"))
				.list();
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.INoteDAO#getConceptNoteCount(java.lang.String)
	 */
	@Override
	public Long getConceptNoteCount(String conceptId) {
		return (Long) getCurrentSession()
				.createCriteria(Note.class)
				.add(Restrictions.eq("concept.identifier", conceptId))
				.setProjection(Projections.rowCount())
				.list().get(0);
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.INoteDAO#getTermNoteCount(java.lang.String)
	 */
	@Override
	public Long getTermNoteCount(String termId) {
		return (Long) getCurrentSession()
				.createCriteria(Note.class)
				.add(Restrictions.eq("term.identifier", termId))
				.setProjection(Projections.rowCount())
				.list().get(0);
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.INoteDAO#findNotesByThesaurusId(java.lang.String)
	 */
	@Override
	public List<Note> findNotesByThesaurusId(String thesaurusId) {

		DetachedCriteria conceptCriteria = DetachedCriteria.forClass(ThesaurusConcept.class, "tc")
				.add(Restrictions.eq("tc.thesaurus.identifier", thesaurusId))
				.setProjection(Projections.projectionList().add(Projections.property("tc.identifier")));

		DetachedCriteria termCriteria = DetachedCriteria.forClass(ThesaurusTerm.class, "tt")
				.add(Restrictions.eq("tt.thesaurus.identifier", thesaurusId))
				.setProjection(Projections.projectionList().add(Projections.property("tt.identifier")));

		Criteria criteria = getCurrentSession().createCriteria(Note.class, "tn")
				.add(Restrictions.or(
						Subqueries.propertyIn("tn.concept.identifier", conceptCriteria),
						Subqueries.propertyIn("tn.term.identifier", termCriteria)));

		return criteria.list();
	}
}
