package my.mimos.mdc.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_trx_attachement")
public class Attachment {
	
	@Id
	@Column(name = "upload_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long uploadId;
	
	@Column(name = "upload_title")
	private String uploadTitle;
	
	@Column(name = "upload_size")
	private Long uploadSize;
	
	@Column(name = "content_type")
	private  String contentType;
	
	@Column(name = "upload_date")
	private Date uploadDate;
	
	@Column(name = "content_data")
    private byte[] content;
	
	@ManyToOne
	@JoinColumn(name = "query_id")
	private Query query;
	
	@ManyToOne
	@JoinColumn(name = "response_id")
	private Response response;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "upload_by", referencedColumnName = "user_id")
	private User uploadBy;
	
	@ManyToOne
	@JoinColumn(name = "message_id")
	private BroadCast broadCast;
	
	@ManyToOne
	@JoinColumn(name = "resp_id")
	private BroadCastResponse broadCastResponse;

}
