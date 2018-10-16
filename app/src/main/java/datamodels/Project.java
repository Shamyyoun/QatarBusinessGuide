package datamodels;

public class Project {
	private String name;
	private String description;
	private String webSite;
	private int imageResource;
    private boolean expanded; // used for listview only

    public Project() {
    }

    public Project(String name, String description, int imageResource) {
		this.name = name;
		this.description = description;
		this.imageResource = imageResource;
	}

	public Project(String name, String description, String webSite, int imageResource) {
		this.name = name;
		this.description = description;
		this.webSite = webSite;
		this.imageResource = imageResource;
	}

	public Project setName(String name) {
		this.name = name;
        return this;
	}

	public String getName() {
		return name;
	}

	public Project setDescription(String description) {
		this.description = description;
        return this;
	}

	public String getDescription() {
		return description;
	}

	public Project setWebSite(String webSite) {
		this.webSite = webSite;
        return this;
	}

	public String getWebSite() {
		return webSite;
	}

	public Project setImageResource(int imageResource) {
		this.imageResource = imageResource;
        return this;
	}

	public int getImageResource() {
		return imageResource;
	}

    public Project setExpanded(boolean expanded) {
        this.expanded = expanded;
        return this;
    }

    public boolean isExpanded() {
        return expanded;
    }
}
