package erp_multi_swing.table;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

@SuppressWarnings("serial")
public abstract class AbstractTablePanel<T> extends JPanel {
	private JScrollPane scrollPane;
	protected JTable table;
	protected NotEditableModel model;

	public AbstractTablePanel(String title) {
		initialize(title);
	}
	
	private void initialize(String title) {
		setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		
		scrollPane.setViewportView(table);	
	}
	
	protected abstract void setWidthAlign();

	
	public void setPopupMenu(JPopupMenu popupMenu) {
		scrollPane.setComponentPopupMenu(popupMenu);
		table.setComponentPopupMenu(popupMenu);
	}
	
	public void loadData(List<T> items) {
		model = new NotEditableModel(getRows(items), getColNames());
		table.setModel(model);

		setWidthAlign();
		
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		
//		table.getColumnModel().getColumn(2).setCellRenderer(new ReturnTableCellRenderer());
	}
	
	
	protected void tableCellAlign(int align, int...idx) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(align);
		
		TableColumnModel cModel = table.getColumnModel();
		for(int i=0; i<idx.length; i++) {
			cModel.getColumn(idx[i]).setCellRenderer(dtcr);
		}
	}
	
	protected void tableSetWidth(int...width) {
		TableColumnModel cModel = table.getColumnModel();
		for(int i=0; i<width.length; i++) {
			cModel.getColumn(i).setPreferredWidth(width[i]);
		}
	}
	
	protected abstract String[] getColNames();
	
	protected Object[][] getRows(List<T> items) {
		Object[][] rows = new Object[items.size()][];
		for(int i=0; i<rows.length; i++) {
			rows[i] = toArray(items.get(i));
		}
		return rows;
	}
	
	/**
	 * @param item
	 * @return new Object[] {item.getDeptNo(), item.getDeptName(), item.getFloor()};
	 */
	protected abstract Object[] toArray(T item);
	
	public void removeRow(){
		int selectedIdx = getSelectedRowIndex();
		model.removeRow(selectedIdx);
	}
	
	/**
	 * @param item
	 * @param row
	 * @throws SQLException
	 * ex)
	 * model.setValueAt(item.getDeptNo(), row, 0);
		model.setValueAt(item.getDeptName(), row, 1);
		model.setValueAt(item.getFloor(), row, 2);
	 */
	public abstract void updateRow(T item, int updateIdx);
	
	public int getSelectedRowIndex() {
		int selectedIdx = table.getSelectedRow();
		if (selectedIdx == -1) {
			throw new RuntimeException("해당 학생을 선택하세요");
		}
		return selectedIdx;
	}
	
	public abstract T getSelectedItem();
	
	public void addRow(T item) {
		model.addRow(toArray(item));
	}

	//수정금지하기위한 모델 선언
	protected class NotEditableModel extends DefaultTableModel{

		public NotEditableModel(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
		
	}
	
/*
	public class ReturnTableCellRenderer extends JLabel implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if (value==null) return this;
			setText(value.toString());
			setOpaque(true);
			setHorizontalAlignment(JLabel.CENTER);
			
			if (Integer.parseInt(table.getValueAt(row, 2).toString())>=90) {
				setBackground(Color.CYAN);
			}else if(Integer.parseInt(table.getValueAt(row, 2).toString())>=80) {
				setBackground(Color.LIGHT_GRAY);
			}
			else {
				setBackground(Color.WHITE);
			}
			if (isSelected) {
				setBackground(Color.orange);
			}
			return this;
		}
	}
*/
	public void clearSelection() {
		table.clearSelection();
	}
	
}