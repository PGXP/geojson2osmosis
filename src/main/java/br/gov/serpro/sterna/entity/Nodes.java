/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.serpro.sterna.entity;

import br.gov.serpro.sterna.util.HstoreUserType;
import com.vividsolutions.jts.geom.Point;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/**
 *
 * @author 70744416353
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(catalog = "geo", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nodes.findAll", query = "SELECT n FROM Nodes n"),
    @NamedQuery(name = "Nodes.findById", query = "SELECT n FROM Nodes n WHERE n.id = :id"),
    @NamedQuery(name = "Nodes.findByVersion", query = "SELECT n FROM Nodes n WHERE n.version = :version"),
    @NamedQuery(name = "Nodes.findByUserId", query = "SELECT n FROM Nodes n WHERE n.userId = :userId"),
    @NamedQuery(name = "Nodes.findByTstamp", query = "SELECT n FROM Nodes n WHERE n.tstamp = :tstamp"),
    @NamedQuery(name = "Nodes.findByChangesetId", query = "SELECT n FROM Nodes n WHERE n.changesetId = :changesetId")})
@TypeDef(name = "hstore", typeClass = HstoreUserType.class)
public class Nodes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private int version;

    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;

    @Basic(optional = false)
    @Column(name = "changeset_id", nullable = false)
    private long changesetId;

    @Type(type = "hstore")
    private Map<String, String> tags;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point geom;

    public Nodes() {
        tags = new ConcurrentHashMap<>();
    }

    public Nodes(Long id) {
        this.id = id;
    }

    public Nodes(Long id, int version, int userId, Date tstamp, long changesetId) {
        this.id = id;
        this.version = version;
        this.userId = userId;
        this.tstamp = tstamp;
        this.changesetId = changesetId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        this.tstamp = tstamp;
    }

    public long getChangesetId() {
        return changesetId;
    }

    public void setChangesetId(long changesetId) {
        this.changesetId = changesetId;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public void setTags(String grupo, String tipo) {
        if (tipo == null) {
            this.tags.putIfAbsent(grupo, "S/N");
        } else {
            this.tags.putIfAbsent(grupo, tipo.replace("\"", "&quot;"));
        }
    }

    public Point getGeom() {
        return geom;
    }

    public void setGeom(Point geom) {
        this.geom = geom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nodes)) {
            return false;
        }
        Nodes other = (Nodes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nodes{" + "id=" + id + ", version=" + version + ", userId=" + userId + ", tstamp=" + tstamp + ", changesetId=" + changesetId + ", tags=" + tags + ", geom=" + geom + '}';
    }

}
