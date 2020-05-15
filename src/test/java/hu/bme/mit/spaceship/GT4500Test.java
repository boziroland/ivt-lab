package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore primary;
  private TorpedoStore secondary;

  @BeforeEach
  public void init(){
    primary = mock(TorpedoStore.class);
    secondary = mock(TorpedoStore.class);

    this.ship = new GT4500(primary, secondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);

    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Once(){

    when(primary.fire(1)).thenReturn(true);


    boolean result = ship.fireTorpedo(FiringMode.SINGLE);


    assertEquals(true, result);

    verify(primary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Twice(){

    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);


    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
	boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);
	
	assertEquals(true, result1);
    assertEquals(true, result2);

    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Both(){

    when(primary.fire(1)).thenReturn(true);
	when(secondary.fire(1)).thenReturn(true);
	when(primary.isEmpty()).thenReturn(false);
	when(secondary.isEmpty()).thenReturn(false);

	boolean result = ship.fireTorpedo(FiringMode.ALL);
	
	assertEquals(true, result);

    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Primary_Empty(){

    when(primary.isEmpty()).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);

    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Both_Empty(){

    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);

    verify(primary, times(0)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Both_Primary_Empty(){

    when(primary.isEmpty()).thenReturn(true);
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);

    verify(primary, times(0)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireLaser(){
	boolean result = ship.fireLaser(FiringMode.ALL);

	assertEquals(false, result);
	verify(primary, times(0)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void firePrimaryWhenItWasFiredLastButIsNotEmpty(){
  
	  when(secondary.isEmpty()).thenReturn(true);
	  when(primary.isEmpty()).thenReturn(false);
	  when(primary.fire(1)).thenReturn(true);
  
	  boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
	  boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);
  
	  assertEquals(true, result1);
	  assertEquals(true, result2);
  
	  verify(primary, times(2)).fire(1);
  }
  @Test
  public void branchCoverage1(){
	  when(secondary.isEmpty()).thenReturn(false);
	  when(primary.isEmpty()).thenReturn(true);
  
  
	  boolean result = ship.fireTorpedo(FiringMode.ALL);
  
	  assertEquals(false, result);
  
	  verify(primary, times(0)).fire(1);
	  verify(secondary, times(0)).fire(1);
  
  }
  @Test
  public void branchCoverage2(){
	  when(secondary.isEmpty()).thenReturn(!false);
	  when(primary.isEmpty()).thenReturn(!true);
  
	  boolean result = ship.fireTorpedo(FiringMode.ALL);
  
	  assertEquals(false, result);
  
	  verify(primary, times(0)).fire(1);
	  verify(secondary, times(0)).fire(1);
  
  }

  @Test
  public void branchCoverage3(){
  
	  boolean result = ship.fireTorpedo(FiringMode.PLACEHOLDER);
  
	  assertEquals(false, result);
  
	  verify(primary, times(0)).fire(1);
	  verify(secondary, times(0)).fire(1);
  
  }
  
  @Test
  public void firePrimaryWhenItWasFiredLastAndIsEmptyTheSecondTime(){
  
	  when(secondary.isEmpty()).thenReturn(true);
	  when(primary.isEmpty()).thenReturn(false);
	  when(primary.fire(1)).thenReturn(true);
  
	  boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);

	  when(primary.isEmpty()).thenReturn(true);

	  boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);
  
	  assertEquals(true, result1);
	  assertEquals(false, result2);
  
	  verify(primary, times(1)).fire(1);
  }
}
