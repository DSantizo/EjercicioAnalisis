/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.mcc.ginco.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fr.mcc.ginco.beans.ExternalThesaurus;
import fr.mcc.ginco.dao.IExternalThesaurusDAO;

/**
 *
 * @author Dennis Santizo
 */
@Repository
public class ExternalThesaurusDAO extends
		GenericHibernateDAO<ExternalThesaurus, Integer> implements
		IExternalThesaurusDAO {

	public ExternalThesaurusDAO() {
		super(ExternalThesaurus.class);
	}

	public List<ExternalThesaurus> findAllByExternalIdQuery(String query) {
		Criteria criteria = getCurrentSession().createCriteria(ExternalThesaurus.class);
		if (query != null && !query.isEmpty()) {
			criteria.add(Restrictions.like("externalId", query + "%"));
		}
		return criteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mcc.ginco.dao.IExternalThesaurusDAO#findBySourceExternalIdId(java.
	 * lang.String)
	 */
	@Override
	public ExternalThesaurus findBySourceExternalId(String externalId) {
		Criteria criteria = getCurrentSession().createCriteria(ExternalThesaurus.class);
		criteria.add(Restrictions.eq("externalId", externalId));
		List<ExternalThesaurus> externalThesauruses = criteria.list();
		if (externalThesauruses != null && externalThesauruses.size() > 0) {
			return externalThesauruses.get(0);
		} else {
			return null;
		}
	}
}
