package com.garrastudios.plaquetometro;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.*;
import android.annotation.SuppressLint;

public class MainActivity extends ActionBarActivity {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
	SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getActionBar().setTitle(R.string.app_name);
        
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //mSectionsPagerAdapter.setPrimaryItem(mViewPager, position, object)

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) { }
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				mViewPager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {	}
		};

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(tabListener));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	switch (position)
        	{
        	case 0:
        		return VolumeFragment.newInstance(position + 1);
        	case 1:
        		return RendimentoFragment.newInstance(position + 1);
        	}
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }

    public static class VolumeFragment extends Fragment {
    	private View RootView = null;
    	
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static VolumeFragment newInstance(int sectionNumber) {
        	VolumeFragment fragment = new VolumeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public VolumeFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            RootView = inflater.inflate(R.layout.fragment_volume, container, false);
            
            TextWatcher tw = new TextWatcher() {
    			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
    			@Override public void beforeTextChanged(CharSequence s, int start, int count,
    					int after) {}
    			@Override
    			public void afterTextChanged(Editable s) {
    				CalculateAndShow();
    			}
    		};
    		OnCheckedChangeListener ccl = new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (checkedId == R.id.radioPrematuro || checkedId == R.id.radioRN) {
						// Set to GRAMAS
						((TextView)RootView.findViewById(R.id.lblPeso2)).setText("g");
						// Set Max Length to 4
						((EditText)RootView.findViewById(R.id.txtPeso)).setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
					} else if (checkedId == R.id.radioHomem || checkedId == R.id.radioMulher) {
						// Set to KG
						((TextView)RootView.findViewById(R.id.lblPeso2)).setText("kg");
						// Set Max Length to 3
						((EditText)RootView.findViewById(R.id.txtPeso)).setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
						// TODO take off 1 char when go back to KG and have 4 digits
					}
					
					CalculateAndShow();
				}
			};
    		
    		((EditText)RootView.findViewById(R.id.txtPlaquetometria)).addTextChangedListener(tw);
    		((EditText)RootView.findViewById(R.id.txtPeso)).addTextChangedListener(tw);
    		((EditText)RootView.findViewById(R.id.txtRendimento)).addTextChangedListener(tw);
    		((RadioGroup)RootView.findViewById(R.id.rdgTipoPessoa)).setOnCheckedChangeListener(ccl);
    		((RadioGroup)RootView.findViewById(R.id.rdgTipoTransfusao)).setOnCheckedChangeListener(ccl);
    		
    		// set txtRendimento text to default (80%)
    		((EditText)RootView.findViewById(R.id.txtRendimento)).setText("80");
    		
    		// clear results
    		ClearResults();
            
            return RootView;
        }
        
    	@SuppressLint("UseSparseArrays") private void CalculateAndShow()
    	{
    		RadioGroup rdgTipoTransfusao = (RadioGroup)RootView.findViewById(R.id.rdgTipoTransfusao);
    		RadioGroup rdgTipoPessa = (RadioGroup)RootView.findViewById(R.id.rdgTipoPessoa);
    		EditText txtPlaquetometria = (EditText)RootView.findViewById(R.id.txtPlaquetometria);
    		EditText txtRendimento = (EditText)RootView.findViewById(R.id.txtRendimento);
    		EditText txtPeso = (EditText)RootView.findViewById(R.id.txtPeso);
    		
    		// ====================================================================================
    		int qtPlaquetasDesejadas = 0;
    		int id;
    		if ((id = rdgTipoTransfusao.getCheckedRadioButtonId()) != -1) {
    			if (id == R.id.radioTerapeutica)
    				qtPlaquetasDesejadas = 100000; // Terapêutica
    			else
    				qtPlaquetasDesejadas = 50000; // Profilática
    		} else {
    			ClearResults();
    			return;
    		}
    		
    		int plaquetometriaInt = 0;
    		if (txtPlaquetometria.getText().length() > 0)
    			plaquetometriaInt = Integer.parseInt(txtPlaquetometria.getText().toString()) * 1000;
    		else {
    			ClearResults();
    			return;
    		}
    		
    		int qtDiferencaPlaquetas = qtPlaquetasDesejadas - plaquetometriaInt;
    		// ====================================================================================
    		
    		// ====================================================================================
    		double volemia = 0;
    		if ((id = rdgTipoPessa.getCheckedRadioButtonId()) != -1) {
    			Map<Integer, Integer> volemiaPorKg = new HashMap<Integer, Integer>();
    			volemiaPorKg.put(R.id.radioHomem, 75);
    			volemiaPorKg.put(R.id.radioMulher, 65);
    			volemiaPorKg.put(R.id.radioRN, 90);
    			volemiaPorKg.put(R.id.radioPrematuro, 110);
    			
    			double pesoDouble = 0;
    			
    			if (txtPeso.getText().length() > 0) {
    				pesoDouble = Double.parseDouble(txtPeso.getText().toString());
    				
    				// Convert from G to KG if needed (Newborn or Premature)
    				if (rdgTipoPessa.getCheckedRadioButtonId() == R.id.radioPrematuro ||
    					rdgTipoPessa.getCheckedRadioButtonId() == R.id.radioRN) {
    					pesoDouble /= 1000; // Convert G to KG
    				}
    				
    				// TODO conversion to Pounds
    			} else {
    				ClearResults();
    				return;
    			}
    			
    			volemia = pesoDouble * volemiaPorKg.get(id);
    		} else {
    			ClearResults();
    			return;
    		}
    		// ====================================================================================
    		
    		// ====================================================================================
    		double kStandard;
    		double kAferese;
    		
    		if (txtRendimento.getText().length() > 0) {
    			double rendimento = Double.parseDouble(txtRendimento.getText().toString()) / 100;
    			kStandard = 1000000000 * rendimento;
    			kAferese  = 1500000000 * rendimento;
    		} else {
    			ClearResults();
    			return;
    		}
    		// ====================================================================================
    		
    		// ====================================================================================
    		double temp = qtDiferencaPlaquetas * volemia * 1000;
    		
    		double finalStandard = temp / kStandard;
    		double finalAferese  = temp / kAferese;
    		// ====================================================================================
    		
    		// ====================================================================================
    		String sS = finalStandard / 50 > 2 ? "s" : "";
    		String sA = finalAferese / 200 > 2 ? "s" : "";
    		String unit = getResources().getString(R.string.unidade);
    		
    		TextView rsltStandard = ((TextView)RootView.findViewById(R.id.rsltStandard));
    		TextView rsltAferese = ((TextView)RootView.findViewById(R.id.rsltAferese));
    		
    		//rsltStandard.setTextSize(55);
    		rsltStandard.setText(String.format("%.1f %s%s (%.0fml)", finalStandard / 55, unit, sS, finalStandard));
    		//rsltAferese.setTextSize(55);
    		rsltAferese.setText(String.format("%.1f %s%s (%.0fml)", finalAferese / 200, unit, sA, finalAferese));
    		// ====================================================================================
    		
    	}
    	
    	private void ClearResults()
    	{
    		TextView rsltStandard = ((TextView)RootView.findViewById(R.id.rsltStandard));
    		TextView rsltAferese = ((TextView)RootView.findViewById(R.id.rsltAferese));
    		
    		
    		String faltamDados = getResources().getString(R.string.missingData);
    		rsltStandard.setTextSize(30);
    		rsltStandard.setText(faltamDados);
    		rsltAferese.setTextSize(30);
    		rsltAferese.setText(faltamDados);
    	}
    }
    
    public static class RendimentoFragment extends Fragment {
    	private View RootView = null;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static RendimentoFragment newInstance(int sectionNumber) {
        	RendimentoFragment fragment = new RendimentoFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public RendimentoFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	RootView = inflater.inflate(R.layout.fragment_rendimento, container, false);
            
            TextWatcher tw = new TextWatcher() {
    			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
    			@Override public void beforeTextChanged(CharSequence s, int start, int count,
    					int after) {}
    			@Override
    			public void afterTextChanged(Editable s) {
    				CalculateAndShow();
    			}
    		};
    		OnCheckedChangeListener ccl = new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (checkedId == R.id.radioPrematuroRend || checkedId == R.id.radioRNRend) {
						// Set to GRAMAS
						((TextView)RootView.findViewById(R.id.lblPesoRend2)).setText("g");
						// Set Max Length to 4
						((EditText)RootView.findViewById(R.id.txtPesoRend)).setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
					} else if (checkedId == R.id.radioHomemRend || checkedId == R.id.radioMulherRend) {
						// Set to KG
						((TextView)RootView.findViewById(R.id.lblPesoRend2)).setText("kg");
						// Set Max Length to 3
						((EditText)RootView.findViewById(R.id.txtPesoRend)).setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});
						// TODO take off 1 char when go back to KG and have 4 digits
					}
					
					CalculateAndShow();
				}
			};
    		
    		((EditText)RootView.findViewById(R.id.txtPlaquetometriaPre)).addTextChangedListener(tw);
    		((EditText)RootView.findViewById(R.id.txtPlaquetometriaPos)).addTextChangedListener(tw);
    		((EditText)RootView.findViewById(R.id.txtPesoRend)).addTextChangedListener(tw);
    		((EditText)RootView.findViewById(R.id.txtAlturaRend)).addTextChangedListener(tw);
    		((EditText)RootView.findViewById(R.id.txtVolume)).addTextChangedListener(tw);
    		((RadioGroup)RootView.findViewById(R.id.rdgTipoPessoaRend)).setOnCheckedChangeListener(ccl);
    		((RadioGroup)RootView.findViewById(R.id.rdgTipoTransfusaoRend)).setOnCheckedChangeListener(ccl);
    		
    		// clear results
    		ClearResults();
            
            return RootView;
        }
        
        @SuppressLint("UseSparseArrays") private void CalculateAndShow() 
        {
        	RadioGroup rdgTipoTransfusao = (RadioGroup)RootView.findViewById(R.id.rdgTipoTransfusaoRend);
    		RadioGroup rdgTipoPessoa = (RadioGroup)RootView.findViewById(R.id.rdgTipoPessoaRend);
    		EditText txtPlaquetometriaPre = (EditText)RootView.findViewById(R.id.txtPlaquetometriaPre);
    		EditText txtPlaquetometriaPos = (EditText)RootView.findViewById(R.id.txtPlaquetometriaPos);
    		EditText txtVolume = (EditText)RootView.findViewById(R.id.txtVolume);
    		EditText txtPeso = (EditText)RootView.findViewById(R.id.txtPesoRend);
    		EditText txtAltura = (EditText)RootView.findViewById(R.id.txtAlturaRend);
    		
    		// ====================================================================================
    		double qtDiferencaPlaquetas = 0;
    		if (txtPlaquetometriaPre.getText().length() > 0) {
    			if (txtPlaquetometriaPos.getText().length() > 0) {
    				qtDiferencaPlaquetas = (Double.parseDouble(txtPlaquetometriaPos.getText().toString()) 
    									   - Double.parseDouble(txtPlaquetometriaPre.getText().toString())) 
    									   * 1000; // multiplies by 1000 cause fields do not contain the last
    											   // three zeros, they're 'xxx' and not 'xxx000'
    			} else {
    				ClearResults();
    				return;
    			}
        	} else {
    			ClearResults();
    			return;
    		}
    		// ====================================================================================    		
    		
    		// ====================================================================================
    		double volemia = 0;
    		double pesoDouble = 0;
    		int id;
    		if ((id = rdgTipoPessoa.getCheckedRadioButtonId()) != -1) {
    			Map<Integer, Integer> volemiaPorKg = new HashMap<Integer, Integer>();
    			volemiaPorKg.put(R.id.radioHomemRend, 75);
    			volemiaPorKg.put(R.id.radioMulherRend, 65);
    			volemiaPorKg.put(R.id.radioRNRend, 90);
    			volemiaPorKg.put(R.id.radioPrematuroRend, 110);
    			
    			if (txtPeso.getText().length() > 0) {
    				pesoDouble = Double.parseDouble(txtPeso.getText().toString());
    				
    				// Convert from G to KG if needed (Newborn or Premature)
    				if (rdgTipoPessoa.getCheckedRadioButtonId() == R.id.radioPrematuroRend ||
						rdgTipoPessoa.getCheckedRadioButtonId() == R.id.radioRNRend) {
    					pesoDouble /= 1000; // Convert G to KG
    				}
    				
    				// TODO conversion to Pounds
    			} else {
    				ClearResults();
    				return;
    			}
    			
    			volemia = pesoDouble * volemiaPorKg.get(id);
    		} else {
    			ClearResults();
    			return;
    		}
    		// ====================================================================================
    		
    		double volume = 0;
    		if (txtVolume.getText().length() > 0) {
    			if ((id = rdgTipoTransfusao.getCheckedRadioButtonId()) != -1) {
    				volume = Double.parseDouble(txtVolume.getText().toString());
    				volume *= id == R.id.radioStandard ? 1000000000 : 1500000000;
        		} else {
        			ClearResults();
        			return;
        		}
    		} else {
    			ClearResults();
    			return;
    		}
    		
    		// RENDIMENTO =========================================================================
    		double rendimento = (qtDiferencaPlaquetas * volemia * 1000) / volume;

    		TextView rsltBox = ((TextView)RootView.findViewById(R.id.rsltBox));
    		rsltBox.setTextSize(55);
    		rsltBox.setText(String.format("%.0f%%", rendimento * 100));
    		// ====================================================================================
    		
    		double superficie = 0;
    		if (txtAltura.getText().length() > 0) {
    			double alturaDouble = Double.parseDouble(txtAltura.getText().toString());
    			superficie = 0.007184 *  Math.pow(alturaDouble, 0.725) * Math.pow(pesoDouble, 0.425);
    		} else {
    			ClearCCI();
    			return;
    		}
    		
    		
    		// CCI ================================================================================
    		double CCI = (qtDiferencaPlaquetas * superficie) / (volume / 100000000000L);

    		TextView rsltCCI = ((TextView)RootView.findViewById(R.id.rsltCCI));
    		rsltCCI.setTextSize(55);
    		rsltCCI.setText(String.format("%.0f/L", CCI));
    		// ====================================================================================    		
    		
        }
        
        private void ClearResults()
        {
        	String faltamDados = getResources().getString(R.string.missingData);
        	
        	TextView rsltBox = ((TextView)RootView.findViewById(R.id.rsltBox));
        	rsltBox.setTextSize(30);
        	rsltBox.setText(faltamDados);
        	
        	ClearCCI();
        }
        
        private void ClearCCI()
        {
        	String faltamDados = getResources().getString(R.string.missingData);
        	
        	TextView rsltBox = ((TextView)RootView.findViewById(R.id.rsltCCI));
        	rsltBox.setTextSize(30);
        	rsltBox.setText(faltamDados);
        }
    }
}
