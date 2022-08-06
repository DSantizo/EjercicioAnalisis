/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mcc.ginco.beans.Suggestion;
import fr.mcc.ginco.dao.ISuggestionDAO;

/**
 *
 * @author Dennis Santizo
 */
@Repository
public class SuggestionDAO extends GenericHibernateDAO<Suggestion, Integer>
		implements ISuggestionDAO {

	public SuggestionDAO() {
		super(Suggestion.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mcc.ginco.dao.ISuggestionDAO#findConceptPaginatedSuggestions(java.
	 * lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Suggestion> findConceptPaginatedSuggestions(String conceptId,
			Integer startIndex, Integer limit) {
		return getCurrentSession().createCriteria(Suggestion.class)
				.setMaxResults(limit)
				.add(Restrictions.eq("concept.identifier", conceptId))
				.setFirstResult(startIndex).addOrder(Order.desc("created"))
				.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mcc.ginco.dao.ISuggestionDAO#findTermPaginatedSuggestions(java.lang
	 * .String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Suggestion> findTermPaginatedSuggestions(String termId,
			Integer startIndex, Integer limit) {
		return getCurrentSession().createCriteria(Suggestion.class)
				.setMaxResults(limit)
				.add(Restrictions.eq("term.identifier", termId))
				.setFirstResult(startIndex).addOrder(Order.desc("created"))
				.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mcc.ginco.dao.ISuggestionDAO#getConceptSuggestionCount(java.lang.String
	 * )
	 */
	@Override
	public Long getConceptSuggestionCount(String conceptId) {
		return (Long) getCurrentSession().createCriteria(Suggestion.class)
				.add(Restrictions.eq("concept.identifier", conceptId))
				.setProjection(Projections.rowCount()).list().get(0);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mcc.ginco.dao.ISuggestionDAO#getTermSuggestionCount(java.lang.String)
	 */
	@Override
	public Long getTermSuggestionCount(String termId) {
		return (Long) getCurrentSession().createCriteria(Suggestion.class)
				.add(Restrictions.eq("term.identifier", termId))
				.setProjection(Projections.rowCount()).list().get(0);
	}

	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.ISuggestionDAO#findPaginatedSuggestionsByRecipient(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Suggestion> findPaginatedSuggestionsByRecipient(
			String recipient, Integer startIndex, Integer limit) {
		return getCurrentSession().createCriteria(Suggestion.class)
				.setMaxResults(limit)
				.add(Restrictions.eq("recipient", recipient))
				.setFirstResult(startIndex).addOrder(Order.desc("created"))
				.list();
	}
	
	/* (non-Javadoc)
	 * @see fr.mcc.ginco.dao.ISuggestionDAO#getSuggestionsByRecipientCount(java.lang.String)
	 */
	@Override
	public Long getSuggestionsByRecipientCount(String recipient) {
		return (Long) getCurrentSession().createCriteria(Suggestion.class)
				.add(Restrictions.eq("recipient", recipient))
				.setProjection(Projections.rowCount()).list().get(0);
	}

}