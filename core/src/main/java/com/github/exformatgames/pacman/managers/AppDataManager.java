package com.github.exformatgames.pacman.managers;

import com.github.exformatgames.pacman.data.AppData;

public class AppDataManager {

	private AppDataServiceI service;

	private AppData data;

	public void saveData (AppData data) {
		this.data = data;
		service.save(data);
	}

	public void saveData () {
		service.save(data);
	}

	public void loadData () {
		service.load();
	}

	public void setData (AppData data) {
		this.data = data;
	}

	public AppData getData () {
		return data;
	}

	public void setService (AppDataServiceI service) {
		this.service = service;
	}

    public abstract static class AppDataServiceI {

        private AppDataManager manager;

        public abstract void save(AppData data);

        public abstract void load();

        protected void loadRequest(AppData data){
            manager.setData(data);
        }

        public void setManager(AppDataManager manager) {
            this.manager = manager;
        }
    }
}
