package erp_multi_swing.content;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import erp_multi_common.dto.Title;

@SuppressWarnings("serial")
public class TitlePanel extends AbsItemPanel<Title> {
	private JTextField tfTitleNo;
	private JTextField tfTitleName;
	
	public TitlePanel() {
		initialize();
	}
	
	private void initialize() {
		setBorder(new TitledBorder(null, "직책 정보", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0, 2, 10, 10));

		JLabel lblTitleNo = new JLabel("직책 번호");
		lblTitleNo.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTitleNo);

		tfTitleNo = new JTextField();
		add(tfTitleNo);
		tfTitleNo.setColumns(10);

		JLabel lblTitleName = new JLabel("직책명");
		lblTitleName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTitleName);

		tfTitleName = new JTextField();
		tfTitleName.setColumns(10);
		add(tfTitleName);
	}
	
	@Override
	public Title getItem() {
		int titleNo = Integer.parseInt(tfTitleNo.getText().trim());
		String titleName = tfTitleName.getText().trim();
		return new Title(titleNo, titleName);
	}

	@Override
	public void setItem(Title item) {
		tfTitleNo.setText(item.getTitleNo()+"");
		tfTitleName.setText(item.getTitleName());
	}

	@Override
	public void clearTf() {
		tfTitleNo.setText("");
		tfTitleName.setText("");		
	}

}
