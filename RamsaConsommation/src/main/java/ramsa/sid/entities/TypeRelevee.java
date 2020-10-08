package ramsa.sid.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TypeRelevee {
	@Id
		private int id_type;
		private String description;
		public int getId_type() {
			return id_type;
		}
		public void setId_type(int id_type) {
			this.id_type = id_type;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
}
