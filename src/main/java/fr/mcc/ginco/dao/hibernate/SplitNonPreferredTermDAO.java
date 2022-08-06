/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mcc.ginco.beans.SplitNonPreferredTerm;
import fr.mcc.ginco.dao.ISplitNonPreferredTermDAO;

/**
 *
 * @author Dennis Santizo
 */
@Repository
public class SplitNonPreferredTermDAO extends
		GenericHibernateDAO<SplitNonPreferredTerm, String> implements
		ISplitNonPreferredTermDAO {

	public SplitNonPreferredTermDAO() {
		super(SplitNonPreferredTerm.class);
	}

	@Override
	public List<SplitNonPreferredTerm> findItems(Integer start, Integer limit,
			String idThesaurus) {
		Criteria criteria = getCurrentSession().createCriteria(
				SplitNonPreferredTerm.class);
		criteria.setMaxResults(limit)
				.add(Restrictions.eq("thesaurus.identifier", idThesaurus))
				.setFirstResult(start).addOrder(Order.asc("lexicalValue"));
		return criteria.list();
	}

	@Override
	public Long countItems(String idThesaurus) {
		Criteria criteria = getCurrentSession().createCriteria(
				SplitNonPreferredTerm.class);
		criteria.add(Restrictions.eq("thesaurus.identifier", idThesaurus))
				.setProjection(Projections.rowCount());
		return (Long) criteria.list().get(0);
	}

}