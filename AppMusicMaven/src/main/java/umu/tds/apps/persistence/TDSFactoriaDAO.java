package umu.tds.apps.persistence;

public class TDSFactoriaDAO extends FactoriaDAO {
	public TDSFactoriaDAO() {}
	
	@Override
	public ISongAdapterDAO getSongDAO() {
		return SongAdapterTDS.getInstance();
	}

	@Override
	public IUserAdapterDAO getUserDAO() {
		return UserAdapterTDS.getInstance();
	}

	@Override
	public IPlayListAdapterDAO getPlayListDAO() {
		return PlayListAdapterTDS.getInstance();
	}
	
	
}
