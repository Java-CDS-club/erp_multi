package erp_multi_swing.table;

import javax.swing.SwingConstants;

import erp_multi_common.dto.Title;

@SuppressWarnings("serial")
public class TitleTablePanel extends AbstractTablePanel<Title> {

	public TitleTablePanel() {
		super("직책 목록");
	}

	@Override
	protected void setWidthAlign() {
		// 직책번호, 직책명은 가운데 정렬
		tableCellAlign(SwingConstants.CENTER, 0, 1);
		// 직책번호, 직책명의 폭을 (100, 200)으로 가능하면 설정
		tableSetWidth(150, 300);		
	}

	@Override
	protected String[] getColNames() {
		return new String[] { "직책번호", "직책명"};
	}

	@Override
	protected Object[] toArray(Title item) {
		return new Object[] {item.getTitleNo(), item.getTitleName()};

	}

	@Override
	public void updateRow(Title item, int updateIdx) {
		model.setValueAt(item.getTitleNo(), updateIdx, 0);
		model.setValueAt(item.getTitleName(), updateIdx, 1);
//		clearSelection();
	}

	@Override
	public Title getSelectedItem() {
		int row = getSelectedRowIndex();
		int titleNo=	(int) model.getValueAt(row, 0);
		String titleName = (String) model.getValueAt(row, 1);
		return new Title(titleNo, titleName);
	}

}
