!classDefinition: #NotFound category: #'CodigoRepetido-Ejercicio'!
Error subclass: #NotFound
	instanceVariableNames: ''
	classVariableNames: ''
	poolDictionaries: ''
	category: 'CodigoRepetido-Ejercicio'!


!classDefinition: #IdiomTest category: #'CodigoRepetido-Ejercicio'!
TestCase subclass: #IdiomTest
	instanceVariableNames: 'tiempodeMetodo customerBook'
	classVariableNames: ''
	poolDictionaries: ''
	category: 'CodigoRepetido-Ejercicio'!

!IdiomTest methodsFor: 'support' stamp: 'A 9/6/2017 20:46:35'!
aMessage: aBlockClosure runInLessThan: milliseconds

	| millisecondsBeforeRunning millisecondsAfterRunning |
	  
	millisecondsBeforeRunning := Time millisecondClockValue.
	aBlockClosure value.
	millisecondsAfterRunning := Time millisecondClockValue.
	
	^ ((millisecondsAfterRunning-millisecondsBeforeRunning) < milliseconds)! !

!IdiomTest methodsFor: 'support' stamp: 'A 9/6/2017 20:27:33'!
aMessageShouldFail: aBlockClosure
	aBlockClosure value.
	self fail.! !


!IdiomTest methodsFor: 'testing' stamp: 'A 9/6/2017 20:55:41'!
test01AddingCustomerShouldNotTakeMoreThan50Milliseconds

	"| customerBook millisecondsBeforeRunning millisecondsAfterRunning |
	
	customerBook := CustomerBook  new.
	
	millisecondsBeforeRunning := Time millisecondClockValue.
	customerBook addCustomerNamed: 'John Lennon'.
	millisecondsAfterRunning := Time millisecondClockValue.
	
	self assert: (millisecondsAfterRunning-millisecondsBeforeRunning) < 50 "


	| customerBook |
	
	customerBook := CustomerBook  new.
	
	self assert: (self aMessage: [customerBook addCustomerNamed: 'John Lennon'] runInLessThan: 50) 

	
	
	
	
! !

!IdiomTest methodsFor: 'testing' stamp: 'A 9/6/2017 20:57:43'!
test02RemovingCustomerShouldNotTakeMoreThan100Milliseconds

	"| customerBook millisecondsBeforeRunning millisecondsAfterRunning paulMcCartney |
	
	customerBook := CustomerBook  new.
	paulMcCartney := 'Paul McCartney'.
	
	customerBook addCustomerNamed: paulMcCartney.
	  
	millisecondsBeforeRunning := Time millisecondClockValue.
	customerBook removeCustomerNamed: paulMcCartney.
	millisecondsAfterRunning := Time millisecondClockValue.
	
	self assert: (millisecondsAfterRunning-millisecondsBeforeRunning) < 100 "

	| customerBook paulMcCartney |
	
	customerBook := CustomerBook  new.
	paulMcCartney := 'Paul McCartney'.
	
	customerBook addCustomerNamed: paulMcCartney.	

	self assert: (self aMessage: [customerBook removeCustomerNamed: paulMcCartney] runInLessThan: 100) 

! !

!IdiomTest methodsFor: 'testing' stamp: 'A 9/6/2017 20:30:49'!
test03CanNotAddACustomerWithEmptyName
	
	| customerBook |
			
	customerBook := CustomerBook new.
	
	"[ customerBook addCustomerNamed: ''.
	self fail ]"
	[ self aMessageShouldFail: [ customerBook addCustomerNamed: '' ]]
		on: Error
		do: [ :anError |
			self assert: anError messageText = CustomerBook customerCanNotBeEmptyErrorMessage.
			self assert: customerBook isEmpty ].! !

!IdiomTest methodsFor: 'testing' stamp: 'A 9/6/2017 20:30:10'!
test04CanNotRemoveAnInvalidCustomer
	
	| customerBook johnLennon |
			
	customerBook := CustomerBook new.
	johnLennon := 'John Lennon'.
	customerBook addCustomerNamed: johnLennon.
	
	"[ customerBook removeCustomerNamed: 'Paul McCartney'.
	self fail ]"
	[ self aMessageShouldFail: [ customerBook removeCustomerNamed: 'Paul McCartney' ]]
		on: NotFound
		do: [ :anError |
			self assert: customerBook numberOfCustomers = 1.
			self assert: (customerBook includesCustomerNamed: johnLennon) ].! !


!classDefinition: #CustomerBook category: #'CodigoRepetido-Ejercicio'!
Object subclass: #CustomerBook
	instanceVariableNames: 'customers'
	classVariableNames: ''
	poolDictionaries: ''
	category: 'CodigoRepetido-Ejercicio'!

!CustomerBook methodsFor: 'testing' stamp: 'HernanWilkinson 7/6/2011 17:56'!
includesCustomerNamed: aName

	^customers includes: aName ! !

!CustomerBook methodsFor: 'testing' stamp: 'HernanWilkinson 7/6/2011 17:48'!
isEmpty
	
	^customers isEmpty  ! !


!CustomerBook methodsFor: 'initialization' stamp: 'HernanWilkinson 7/6/2011 17:42'!
initialize

	super initialize.
	customers := OrderedCollection new! !


!CustomerBook methodsFor: 'customer management' stamp: 'HernanWilkinson 7/6/2011 17:42'!
addCustomerNamed: aName

	aName isEmpty ifTrue: [ self signalCustomerNameCannotBeEmpty ].
	(customers includes: aName) ifTrue: [ self signalCustomerAlreadyExists ].
	
	customers add: aName ! !

!CustomerBook methodsFor: 'customer management' stamp: 'HernanWilkinson 7/6/2011 17:56'!
numberOfCustomers
	
	^customers size! !

!CustomerBook methodsFor: 'customer management' stamp: 'HAW 4/14/2017 16:55:43'!
removeCustomerNamed: aName
 
	customers remove: aName ifAbsent: [ NotFound signal ]! !

!CustomerBook methodsFor: 'customer management' stamp: 'HernanWilkinson 7/6/2011 17:52'!
signalCustomerAlreadyExists 

	self error: self class customerAlreadyExistsErrorMessage! !

!CustomerBook methodsFor: 'customer management' stamp: 'HernanWilkinson 7/6/2011 17:51'!
signalCustomerNameCannotBeEmpty 

	self error: self class customerCanNotBeEmptyErrorMessage ! !

"-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- "!

!classDefinition: 'CustomerBook class' category: #'CodigoRepetido-Ejercicio'!
CustomerBook class
	instanceVariableNames: ''!

!CustomerBook class methodsFor: 'error messages' stamp: 'HernanWilkinson 7/6/2011 17:57'!
customerAlreadyExistsErrorMessage

	^'Customer already exists'! !

!CustomerBook class methodsFor: 'error messages' stamp: 'HernanWilkinson 7/6/2011 17:53'!
customerCanNotBeEmptyErrorMessage

	^'Customer name cannot be empty'! !
